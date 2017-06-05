/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import ships.exception.CollidesWithAnotherShipException;
import ships.exception.NoShipsAvailableException;
import ships.exception.OutsideOfMapPlacementException;
import ships.model.Map;
import ships.model.Ship;
import ships.model.Field;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Queue;

/**
 *
 * @author r4pt0r
 */
public class TCPClientConnection extends Connection {

    private final Socket sock;
    private Thread connLoop;
    private Queue<Field> playerMoveQueue;
    private Queue<Field> opponentMoveQueue;
    private Map playerMap;
    private Map opponentMap;

    private boolean mapSent = false;

    public TCPClientConnection(
            String ipAddress, Integer port,
            Queue<Field> playerMoveQueue, final Queue<Field> opponentMoveQueue,
            Map playerMap, final Map opponentMap
    ) throws IOException {

        this.playerMoveQueue = playerMoveQueue;
        this.opponentMoveQueue = opponentMoveQueue;

        this.playerMap = playerMap;
        this.opponentMap = opponentMap;

        sock = new Socket(ipAddress, port);
        is = sock.getInputStream();
        os = sock.getOutputStream();

        connLoop = new Thread(new ConnectionLoop());
        connLoop.start();
    }

    public TCPClientConnection(Socket socket) {
        super();
        this.sock = socket;
        throw new UnsupportedOperationException("mock player queues and map");
        //connLoop = new Thread(new ConnectionLoop());
        //connLoop.start();
    }

    private class ConnectionLoop implements Runnable {

        @Override
        public void run() {
            while(sock.isConnected() && ! mapSent) {
                //check if we have something to send
                if (playerMap.isDeploymentFinished()) {
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
                    return;
                }
                if (packet instanceof MovePacket) {
                    opponentMoveQueue.add(((MovePacket)packet).getField());
                }
                else if (packet instanceof MapPacket && !opponentMap.isDeploymentFinished()) {
                    List<Ship> ships = ((MapPacket)packet).getShips();
                    for (Ship ship : ships) {
                        try {
                            opponentMap.placeShip(ship);
                        } catch (CollidesWithAnotherShipException ex) {
                            Logger.getLogger(TCPServerConnection.class.getName()).log(Level.INFO, null, ex);
                        } catch (NoShipsAvailableException ex) {
                            Logger.getLogger(TCPServerConnection.class.getName()).log(Level.INFO, null, ex);
                        } catch (OutsideOfMapPlacementException ex) {
                            Logger.getLogger(TCPServerConnection.class.getName()).log(Level.INFO, null, ex);
                        }
                    }
                }
            }
        }
    }
}
