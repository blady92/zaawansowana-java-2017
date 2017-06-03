/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.view;

import ships.exception.OutsideOfMapPlacementException;
import ships.model.ShipPlacementMode;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class MapView extends Canvas {

    public static final Color SHIP_COLOR = Color.GRAY;
    public static final Color HIT_COLOR = Color.YELLOW;
    public static final Color SUNK_COLOR = Color.BLACK;
    public static final Color MISSED_COLOR = Color.LIGHT_GRAY;

    private Color backgroundColor = Color.WHITE;
    private final Integer mapSize = 10;
    private final Color[][] bgs = new Color[mapSize][mapSize];
    protected ShipPlacementMode mode;

    private List<MapClickObserver> observers;

    public MapView() {
        super();
        observers = new ArrayList<>();
        MouseEventObserver ml = new MouseEventObserver();
        this.addMouseListener(ml);
        this.addMouseMotionListener(ml);
        mode = new ShipPlacementMode();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        grphcs.setColor(backgroundColor);
        Rectangle bounds = this.getBounds();
        grphcs.fillRect(0, 0, bounds.width, bounds.height);
        fillFields(grphcs);
        grphcs.setColor(Color.BLACK);
        grphcs.drawRect(0, 0, bounds.width - 1, bounds.height - 1);
        drawGrid(grphcs);
    }

    private void drawGrid(Graphics grphcs) {
        Rectangle bounds = this.getBounds();
        grphcs.setColor(Color.BLACK);
        //vertical lines
        for (int i = 1; i < mapSize; i++) {
            grphcs.drawLine(i * (bounds.width / mapSize), 0, i * (bounds.width / mapSize), bounds.height);
        }

        //horizontal lines
        for (int i = 1; i < mapSize; i++) {
            grphcs.drawLine(0, i * (bounds.height / mapSize), bounds.width, i * (bounds.height / mapSize));
        }
    }

    private void fillFields(Graphics grphcs) {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                Color clr = bgs[i][j];
                if (clr != null) {
                    fillField(i, j, bgs[i][j], grphcs);
                }
            }
        }
    }

    private void fillField(int row, int col, Color color, Graphics grphcs) {
        grphcs.setColor(color);
        grphcs.fillRect(
                col * (this.getBounds().width / mapSize) + 1,
                row * (this.getBounds().height / mapSize) + 1,
                (this.getBounds().width / mapSize) - 1,
                (this.getBounds().height / mapSize) - 1
        );
    }

    public void fillField(int row, int col, Color color) throws OutsideOfMapPlacementException {
        if (row < 0 || row >= mapSize || col < 0 || col >= mapSize)
            throw new OutsideOfMapPlacementException();
        bgs[row][col] = color;
        this.repaint();
    }

    public void clearField(int row, int col) throws OutsideOfMapPlacementException {
        if (row < 0 || row >= mapSize || col < 0 || col >= mapSize)
            throw new OutsideOfMapPlacementException();
        bgs[row][col] = null;
        this.repaint();
    }

    public void clearAllFields(Color clr) {
        for (int i = 0; i < bgs.length; i++) {
            for (int j = 0; j < bgs[i].length; j++) {
                if (bgs[i][j] == clr) {
                    bgs[i][j] = null;
                }
            }
        }
        this.repaint();
    }

    public boolean isFieldFilled(int row, int col) {
        return bgs[row][col] != null;
    }


    /**
     * @return map background
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param background map background to set
     */
    public void setBackgroundColor(Color background) {
        this.backgroundColor = background;
        this.repaint();
    }

    public void addFieldSelectObserver(MapClickObserver o) {
        observers.add(o);
    }

    private class MouseEventObserver extends MouseInputAdapter {

        private int lastRow = 0, lastCol = 0;

        @Override
        public void mouseClicked(MouseEvent me) {
            mouseEvent(me, false);
        }

        @Override
        public void mouseMoved(MouseEvent me) {
            mouseEvent(me, true);
        }

        private void mouseEvent(MouseEvent me, boolean selectedRowChanged) {
            Rectangle bounds = MapView.this.getBounds();
            int row = me.getY() * 10 / bounds.height;
            int col = me.getX() * 10 / bounds.width;
            if (selectedRowChanged && (row != lastRow || col != lastCol)) {
                lastCol = col;
                lastRow = row;
            } else if (selectedRowChanged)
                return;
            FieldSelectEvent fce = new FieldSelectEventImpl(row, col, me.getButton());
            for (MapClickObserver o : observers) {
                o.fieldClickedEvent(fce, MapView.this);
            }
        }
    }
}