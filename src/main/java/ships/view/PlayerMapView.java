package ships.view;

import ships.exception.CollidesWithAnotherShipException;
import ships.exception.NoShipsAvailableException;
import ships.exception.OutsideOfMapPlacementException;
import ships.model.*;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerMapView extends MapView {
    private MapClickObserver mco = new MapClickObserver();
    private Map game = null;

    public PlayerMapView(Map gameMap) {
        this.game = gameMap;
        this.addFieldSelectObserver(mco);
    }

    private void showShipsOnMap() {
        for (int i = 0; i < GameMap.mapSize; i++) {
            for (int j = 0; j < GameMap.mapSize; j++) {
                if (game.getField(i, j).isShipHere()) {
                    try {
                        fillField(i, j, Color.GRAY);
                    } catch (OutsideOfMapPlacementException ex) {
                        Logger.getLogger(PlayerMapView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
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

    public class MapClickObserver implements ships.view.MapClickObserver {
        @Override
        public void fieldClickedEvent(FieldSelectEvent fce, MapView bm) {
            try {
                if (game.isDeploymentFinished()) {
                    bm.setBackgroundColor(Color.LIGHT_GRAY);
                    return;
                }
                if (fce.getButton() == FieldSelectEventImpl.NOBUTTON) {
                    //move
                    bm.clearAllFields(Color.GREEN);
                    bm.clearAllFields(Color.RED);
                    if (!mode.isActive()) {
                        return;
                    }
                    showShipsOnMap();
                    Ship ship = new Ship(
                            mode.getSize(),
                            new FieldImpl(fce.getRow(), fce.getCol()),
                            mode.getDir()
                    );
                    java.util.List<Field> conflicts = game.isAbleToPlaceShip(ship);
                    for (Field f : ship.getFieldList()) {
                        try {
                            bm.fillField(f.getRow(), f.getCol(), Color.GREEN);
                        } catch (OutsideOfMapPlacementException ex) {/*intentionally do nothing*/}
                    }
                    for (Field f : conflicts) {
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
                if (fce.getButton() != FieldSelectEventImpl.BUTTON1) {
                    //unsupported mouse button
                    return;
                }
                //left
                Ship ship = new Ship(mode.getSize(), new FieldImpl(fce.getRow(), fce.getCol()), mode.getDir());
                if (mode.isActive() && game.isAbleToPlaceShip(ship).isEmpty()) {
                    game.placeShip(ship);
                    showShipsOnMap();
                    stopPlacement();
                }
            } catch (OutsideOfMapPlacementException ex) {
                Logger.getLogger(BattleshipsGame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CollidesWithAnotherShipException | NoShipsAvailableException ex) {
                Logger.getLogger(PlayerMapView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}