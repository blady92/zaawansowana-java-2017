package ships.view;

import ships.exception.OutsideOfMapPlacementException;
import ships.exception.ShipNotFoundException;
import ships.model.Field;
import ships.model.GameMap;
import ships.model.Map;
import ships.model.MapChangeObserver;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OpponentMapView extends MapView {
    private Map game = null;

    public OpponentMapView(Map gameMap) {
        this.game = gameMap;
        this.game.addMapChangeObserver(new ChangeObserver());
    }

    /**
     * Cheating routine
     * DO NOT USE IN PRODUCTION !!!
     */
    @Deprecated
    private void showShipsOnMap() {
        for (int i = 0; i < GameMap.mapSize; i++) {
            for (int j = 0; j < GameMap.mapSize; j++) {
                if (game.getField(i, j).isShipHere()) {
                    try {
                        fillField(i, j, Color.MAGENTA);
                    } catch (OutsideOfMapPlacementException ex) {
                        Logger.getLogger(PlayerMapView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void showHitsOnMap() {
        for (int i = 0; i < GameMap.mapSize; i++) {
            for (int j = 0; j < GameMap.mapSize; j++) {
                try {
                    Field f = game.getField(i, j);
                    if (f.isAttacked()) {
                        if (!f.isShipHere()) {
                            fillField(i, j, MISSED_COLOR);
                            continue;
                        }
                        try {
                            if (game.getShipAtPosition(f).isSunken()) {
                                fillField(i, j, SUNK_COLOR);
                                continue;
                            }
                        } catch (ShipNotFoundException ex) {
                            /* intentionally do nothing */
                        }
                        fillField(i, j, HIT_COLOR);
                    }
                } catch (OutsideOfMapPlacementException ex) {
                    Logger.getLogger(PlayerMapView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class ChangeObserver implements MapChangeObserver {

        @Override
        public void mapChangedEvent() {
            showHitsOnMap();
        }
    }
}
