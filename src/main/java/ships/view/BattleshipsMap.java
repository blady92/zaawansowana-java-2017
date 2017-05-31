/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputAdapter;
import ships.exception.OutsideOfMapPlacementException;
import ships.model.Ship;
import ships.model.ShipPlacementMode;

public class BattleshipsMap extends Canvas {

    private Color backgroundColor = Color.WHITE;
    private final Integer mapSize = 10;
    private final Color[][] bgs = new Color[mapSize][mapSize];
    private ShipPlacementMode mode;

    private List<BattleshipMapClickObserver> observers;

    public BattleshipsMap() {
        super();
        for (Color[] cs : bgs) {
            for (Color c : cs) {
                c = null;
            }
        }
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
        grphcs.drawRect(0, 0, bounds.width-1, bounds.height-1);
        drawGrid(grphcs);
    }

    private void drawGrid(Graphics grphcs) {
        Rectangle bounds = this.getBounds();
        grphcs.setColor(Color.BLACK);
        //vertical lines
        for (int i = 1; i < mapSize; i++) {
            grphcs.drawLine(i*(bounds.width/mapSize), 0, i*(bounds.width/mapSize), bounds.height);
        }

        //horizontal lines
        for (int i = 1; i < mapSize; i++) {
            grphcs.drawLine(0, i*(bounds.height/mapSize), bounds.width, i*(bounds.height/mapSize));
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
                col*(this.getBounds().width/mapSize)+1,
                row*(this.getBounds().height/mapSize)+1,
                (this.getBounds().width/mapSize)-1,
                (this.getBounds().height/mapSize)-1
        );
    }

    public void fillField(int row, int col, Color color) throws OutsideOfMapPlacementException {
        if(row < 0 || row >= mapSize || col < 0 || col >= mapSize)
            throw new OutsideOfMapPlacementException();
        bgs[row][col] = color;
        this.repaint();
    }

    public void clearField(int row, int col) throws OutsideOfMapPlacementException {
        if(row < 0 || row >= mapSize || col < 0 || col >= mapSize)
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

    public void addFieldSelectObserver(BattleshipMapClickObserver o) {
        observers.add(o);
    }

    public void startPlacement(Ship.Size size) {
        mode.setSize(size);
        mode.activate();
    }

    public void stopPlacement() {
        mode.deactivate();
    }

    public ShipPlacementMode getMode() {
        Logger.getLogger(GUI.class.getName()).log(Level.WARNING, "TODO: remove temporary method");
        return mode;
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
            Rectangle bounds = BattleshipsMap.this.getBounds();
            int row = me.getY()*10/bounds.height;
            int col = me.getX()*10/bounds.width;
            if(selectedRowChanged && ( row != lastRow || col != lastCol ) )
            {
                lastCol = col;
                lastRow = row;
            }
            else if(selectedRowChanged)
                return;
            FieldSelectEvent fce = new FieldSelectEventImpl(row, col, me.getButton());
            for(BattleshipMapClickObserver o : observers) {
                o.fieldClickedEvent(fce, BattleshipsMap.this);
            }
        }
    }
}