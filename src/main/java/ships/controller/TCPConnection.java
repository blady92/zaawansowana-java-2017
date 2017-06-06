package ships.controller;

import ships.exception.CollidesWithAnotherShipException;
import ships.exception.NoShipsAvailableException;
import ships.exception.OutsideOfMapPlacementException;
import ships.model.Field;
import ships.model.Map;
import ships.model.Ship;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPConnection extends Connection {

    protected Socket sock;

    protected volatile Queue<Field> playerMoveQueue;
    protected volatile Queue<Field> opponentMoveQueue;

    protected Map playerMap;
    protected Map opponentMap;

    protected boolean mapSent = false;

    protected class ConnectionLoop implements Runnable {

        @Override
        public void run() {
            while(sock.isConnected()) {
                //check if we have something to send
                if (playerMap.isDeploymentFinished() && ! mapSent) {
                    try {
                        sendPacket(new MapPacket(playerMap.getShips()));
                        mapSent = true;
                    } catch (IOException ex) {
                        Logger.getLogger(TCPServerConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!playerMoveQueue.isEmpty()) {
                    try {
                        sendPacket(new MovePacket(playerMoveQueue.remove()));
                    } catch (IOException ex) {
                        Logger.getLogger(TCPClientConnection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //receive
                try {
                    if (is.available() == 0) {
                        //no data to read, skip
                        continue;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(TCPServerConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
                CommunicationPacket packet;
                try {
                    packet = receivePacket();
                } catch (IOException ex) {
                    Logger.getLogger(TCPServerConnection.class.getName()).log(Level.SEVERE, null, ex);
                    continue;
                }
                if (packet instanceof MovePacket) {
                    opponentMoveQueue.add(((MovePacket)packet).getField());
                }
                else if (packet instanceof MapPacket && !opponentMap.isDeploymentFinished()) {
                    List<Ship> ships = ((MapPacket)packet).getShips();
                    for (Ship ship : ships) {
                        try {
                            opponentMap.placeShip(ship);
                        } catch (CollidesWithAnotherShipException | NoShipsAvailableException | OutsideOfMapPlacementException ex) {
                            Logger.getLogger(TCPServerConnection.class.getName()).log(Level.INFO, null, ex);
                        }
                    }
                }
            }
        }
    }
}
