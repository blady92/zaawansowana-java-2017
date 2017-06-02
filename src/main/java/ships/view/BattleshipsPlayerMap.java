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
    private MapClickObserver pco = new MapClickObserver();
    private Map game = null;

    public BattleshipsPlayerMap(Map gameMap) {
        this.game = gameMap;
        this.addFieldSelectObserver(pco);
    }

    public class MapClickObserver implements BattleshipMapClickObserver {
        @Override
        public void fieldClickedEvent(FieldSelectEvent fce, BattleshipsMap bm) {
            try {
                if(fce.getButton() == FieldSelectEventImpl.NOBUTTON) {
                    //move
                    bm.clearAllFields(Color.GREEN);
                    bm.clearAllFields(Color.RED);
                    if (!bm.getMode().isActive()) {
                        return;
                    }
                    Ship ship = new Ship(
                            bm.getMode().getSize(),
                            new FieldImpl(fce.getRow(), fce.getCol()),
                            bm.getMode().getDir()
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
                    if (bm.getMode().isActive()) {
                        bm.getMode().switchDirection();
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
