/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ships.exception.ShipGameException;
import ships.model.Field;
import ships.model.Ship;
import ships.view.OpponentMapView;
import ships.view.PlayerMapView;

/**
 *
 * @author r4pt0r
 */
public class PlayerGuestedGame extends Game {

    private Connection conn;

    public PlayerGuestedGame() {
        super();

        throw new UnsupportedOperationException("Do we need it?");
    }

    public PlayerGuestedGame(
            PlayerMapView playerMapView, OpponentMapView opponentMapView,
            String ipAddress, Integer port
    ) throws ShipGameException, IOException {
        super(playerMapView, opponentMapView);

        conn = new TCPClientConnection(ipAddress, port);
        throw new UnsupportedOperationException("Wait for server's ship placement");
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
        while (playerMoveQueue.isEmpty()) {
            //wait until player performs a move
        }
        Field f = playerMoveQueue.remove();
        CommunicationPacket packet = new MovePacket(f);
        try {
            conn.sendPacket(packet);
        } catch (IOException ex) {
            Logger.getLogger(PlayerGuestedGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("TODO: send the move to server");
        //return f.isAttacked();
    }

    @Override
    protected Boolean opponentShooting() {
        try {
            CommunicationPacket packet = conn.receivePacket();
        } catch (IOException ex) {
            Logger.getLogger(PlayerGuestedGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
