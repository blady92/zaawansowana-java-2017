/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import ships.model.Field;
import ships.model.Map;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author r4pt0r
 */
public class TCPServerConnection extends TCPConnection {

    private ServerSocket srv;

    private Thread connLoop;

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
}