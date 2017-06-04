/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import ships.exception.ShipGameException;
import ships.model.Ship;
import ships.view.OpponentMapView;
import ships.view.PlayerMapView;

/**
 *
 * @author r4pt0r
 */
public class PlayerHostedGame extends Game {

    private Connection conn;

    public PlayerHostedGame() {
        super();

        throw new UnsupportedOperationException("Do we need it?");
    }

    public PlayerHostedGame(
            PlayerMapView playerMapView, OpponentMapView opponentMapView,
            Integer port
    ) throws ShipGameException {
        super(playerMapView, opponentMapView);

        conn = new TCPServerConnection(port);
        throw new UnsupportedOperationException("What about ship placement");
    }

    @Override
    public void startPlacement(Ship.Size size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stopPlacement() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Boolean playerShooting() {
        while (playerMoveQueue.isEmpty()) {
            //wait until player performs a move
        }
        throw new UnsupportedOperationException("TODO: send the move to client");
        //return playerMoveQueue.remove();
    }

    @Override
    protected Boolean opponentShooting() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
