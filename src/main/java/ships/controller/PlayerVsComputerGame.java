package ships.controller;

import ships.exception.OutsideOfMapPlacementException;
import ships.exception.ShipGameException;
import ships.model.Field;
import ships.model.FieldImpl;
import ships.model.GameMap;
import ships.model.Ship;
import ships.view.OpponentMapView;
import ships.view.PlayerMapView;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerVsComputerGame extends Game {


    public PlayerVsComputerGame() throws ShipGameException {
        super();

        placeComputerShips();
    }

    public PlayerVsComputerGame(PlayerMapView playerMapView, OpponentMapView opponentMapView) throws ShipGameException {
        super(playerMapView, opponentMapView);

        placeComputerShips();
    }

    private void placeComputerShips() throws ShipGameException {
        for (Ship.Size s : Ship.Size.values()) {
            placeComputerShips(s);
        }
    }

    private void placeComputerShips(Ship.Size s) throws ShipGameException {
        Random rand = new Random();
        while(opponentMap.getAvailableShipCount(s)>0)
        {
            Integer x = rand.nextInt(GameMap.mapSize);
            Integer y = rand.nextInt(GameMap.mapSize);
            Boolean dir = rand.nextBoolean();
            Ship ship = new Ship(s, new FieldImpl(x, y), dir ? Ship.Direction.HORIZONTAL : Ship.Direction.VERTICAL);
            if (opponentMap.isAbleToPlaceShip(ship).isEmpty()) {
                opponentMap.placeShip(ship);
            }
        }
    }

    @Override
    protected Boolean opponentShooting() {
        try {
            Field f = null;
            Random rand = new Random();
            do {
                f = playerMap.getField(rand.nextInt(GameMap.mapSize), rand.nextInt(GameMap.mapSize));
            }
            while(f.isAttacked());

            return playerMap.shootAt(f);
        } catch (OutsideOfMapPlacementException ex) {
            Logger.getLogger(PlayerVsComputerGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
