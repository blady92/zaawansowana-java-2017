/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import ships.model.Field;
import ships.model.Map;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

/**
 *
 * @author r4pt0r
 */
public class TCPClientConnection extends TCPConnection {

    private Thread connLoop;

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

    public TCPClientConnection(
            Socket socket,
            Queue<Field> playerMoveQueue, final Queue<Field> opponentMoveQueue,
            Map playerMap, final Map opponentMap
    ) throws IOException {
        super();
        this.sock = socket;

        this.playerMoveQueue = playerMoveQueue;
        this.opponentMoveQueue = opponentMoveQueue;

        this.playerMap = playerMap;
        this.opponentMap = opponentMap;

        is = sock.getInputStream();
        os = sock.getOutputStream();

        connLoop = new Thread(new ConnectionLoop());
        connLoop.start();
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
