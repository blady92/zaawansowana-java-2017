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

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author r4pt0r
 */
public class PlayerHostedGame extends Game {

    private Connection conn;

    public PlayerHostedGame(final Integer port) throws IOException {
        super();

        this.state = State.CONNECTING;
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    conn = new TCPServerConnection(port);
                    setState(State.DEPLOYMENT);
                } catch (IOException ex) {
                    Logger.getLogger(PlayerHostedGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t.start();
    }

    public PlayerHostedGame(
            PlayerMapView playerMapView, OpponentMapView opponentMapView,
            Integer port
    ) throws ShipGameException, IOException {
        super(playerMapView, opponentMapView);

        conn = new TCPServerConnection(port);
    }

    @Override
    protected void setState(State state) {
        super.setState(state);
        if (state == State.BATTLE) {
            List<Ship> ships = playerMap.getShips();
            CommunicationPacket packet = new MapPacket(ships);
            try {
                conn.sendPacket(packet);
            } catch (IOException ex) {
                Logger.getLogger(PlayerGuestedGame.class.getName()).log(Level.SEVERE, null, ex);
                this.setState(State.DEPLOYMENT);
            }
        }
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
        while(playerMoveQueue.isEmpty()) {
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
