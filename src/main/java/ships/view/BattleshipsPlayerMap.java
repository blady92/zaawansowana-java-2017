package ships.view;

import ships.exception.OutsideOfMapPlacementException;
import ships.model.Field;
import ships.model.FieldImpl;
import ships.model.Map;
import ships.model.Ship;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BattleshipsPlayerMap extends BattleshipsMap {
    private MapClickObserver mco = new MapClickObserver();
    private Map game = null;

    public BattleshipsPlayerMap(Map gameMap) {
        this.game = gameMap;
        this.addFieldSelectObserver(mco);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        //TODO: paint player ships
    }

    public void startPlacement(Ship.Size size) {
        if (game.isDeploymentFinished()) {
            return;
        }
        mode.setSize(size);
        mode.activate();
    }

    public void stopPlacement() {
        mode.deactivate();
    }

    public class MapClickObserver implements BattleshipMapClickObserver {
        @Override
        public void fieldClickedEvent(FieldSelectEvent fce, BattleshipsMap bm) {
            try {
                if(fce.getButton() == FieldSelectEventImpl.NOBUTTON) {
                    //move
                    bm.clearAllFields(Color.GREEN);
                    bm.clearAllFields(Color.RED);
                    if (!mode.isActive()) {
                        return;
                    }
                    Ship ship = new Ship(
                            mode.getSize(),
                            new FieldImpl(fce.getRow(), fce.getCol()),
                            mode.getDir()
                    );
                    java.util.List<Field> conflicts = game.isAbleToPlaceShip(ship);
                    for(Field f : ship.getFieldList()) {
                        try {
                            bm.fillField(f.getRow(), f.getCol(), Color.GREEN);
                        }
                        catch(OutsideOfMapPlacementException ex) {/*intentionally do nothing*/}
                    }
                    for(Field f : conflicts) {
                        bm.fillField(f.getRow(), f.getCol(), Color.RED);
                    }
                    return;
                }
                if (fce.getButton() == FieldSelectEventImpl.BUTTON3) {
                    //right
                    if (mode.isActive()) {
                        mode.switchDirection();
                    }
                    return;
                }
                if (fce.getButton() == FieldSelectEventImpl.BUTTON2) {
                    //middle
                    return;
                }
                //left
                if (!bm.isFieldFilled(fce.getRow(), fce.getCol())) {
                    bm.fillField(fce.getRow(), fce.getCol(), Color.yellow);
                }
                else {
                    bm.clearField(fce.getRow(), fce.getCol());
                }
            }
            catch(OutsideOfMapPlacementException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
