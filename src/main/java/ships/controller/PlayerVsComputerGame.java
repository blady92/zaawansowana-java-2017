package ships.controller;

import ships.exception.ShipGameException;
import ships.model.FieldImpl;
import ships.model.GameMap;
import ships.model.Ship;
import ships.view.MapView;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerVsComputerGame extends Game {

    public PlayerVsComputerGame() throws ShipGameException {
        super();
        placeComputerShips();
    }

    @Override
    public void startPlacement(Ship.Size size) {
        playerMapView.startPlacement(size);
    }

    @Override
    public void stopPlacement() {
        playerMapView.stopPlacement();
    }

    @Override
    public MapView getPlayerMapView() {
        return this.playerMapView;
    }

    @Override
    public MapView getOpponentMapView() {
        return this.opponentMapView;
    }

    private void placeComputerShips() throws ShipGameException {
        for (Ship.Size s : Ship.Size.values()) {
            placeComputerShips(s);
        }
    }

    private void placeComputerShips(Ship.Size s) throws ShipGameException {
        Random rand = new Random();
        while (opponentMap.getAvailableShipCount(s) > 0) {
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
        Logger.getLogger(Game.class.getName()).log(Level.INFO, "ping");
        return false;
    }

}
