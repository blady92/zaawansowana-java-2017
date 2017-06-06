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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author r4pt0r
 */
public class TCPServerConnection extends Connection {

    private ServerSocket srv;
    private final Socket sock;
    private Thread connLoop;
    private volatile Queue<Field> playerMoveQueue;
    private volatile Queue<Field> opponentMoveQueue;
    private Map playerMap;
    private Map opponentMap;

    private boolean mapSent = false;

    public TCPServerConnection(
            Integer port,
            Queue<Field> playerMoveQueue, final Queue<Field> opponentMoveQueue,
            Map playerMap, final Map opponentMap
    ) throws IOException {

        this.playerMoveQueue = playerMoveQueue;
        this.opponentMoveQueue = opponentMoveQueue;

        this.playerMap = playerMap;
        this.opponentMap = opponentMap;

        srv = new ServerSocket(port);
        //what thread are we in? same as gui?
        sock = srv.accept();
        is = sock.getInputStream();
        os = sock.getOutputStream();
        connLoop = new Thread(new ConnectionLoop());
        connLoop.start();
    }

    public TCPServerConnection(
            ServerSocket server, Socket socket,
            Queue<Field> playerMoveQueue, final Queue<Field> opponentMoveQueue,
            Map playerMap, final Map opponentMap
    ) {
        super();

        this.srv = server;
        this.sock = socket;

        this.playerMoveQueue = playerMoveQueue;
        this.opponentMoveQueue = opponentMoveQueue;

        this.playerMap = playerMap;
        this.opponentMap = opponentMap;

        try {

            is = sock.getInputStream();
            os = sock.getOutputStream();
            connLoop = new Thread(new ConnectionLoop());
            connLoop.start();
        } catch (IOException ex) {
            Logger.getLogger(TCPServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param playerMoveQueue the playerMoveQueue to set
     */
    public void setPlayerMoveQueue(Queue<Field> playerMoveQueue) {
        if (this.playerMoveQueue == null) {
            this.playerMoveQueue = playerMoveQueue;
        }
    }

    /**
     * @param opponentMoveQueue the opponentMoveQueue to set
     */
    public void setOpponentMoveQueue(Queue<Field> opponentMoveQueue) {
        if (opponentMoveQueue == null) {
            this.opponentMoveQueue = opponentMoveQueue;
        }
    }

    private class ConnectionLoop implements Runnable {

        @Override
        public void run() {
            while(sock.isConnected()) {
                //check if we have something to send
                if (playerMap.isDeploymentFinished() && !mapSent) {
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