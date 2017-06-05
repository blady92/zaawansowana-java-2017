/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import ships.exception.OutsideOfMapPlacementException;
import ships.exception.ShipGameException;
import ships.model.Field;
import ships.model.Ship;
import ships.view.OpponentMapView;
import ships.view.PlayerMapView;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author r4pt0r
 */
public class PlayerHostedGame extends Game {

    private Connection conn;
    protected Queue<Field> playerMoveQueueForRemote = new ConcurrentLinkedQueue<>();

    public PlayerHostedGame(final Integer port) throws IOException {
        super();

        this.state = State.CONNECTING;
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    conn = new TCPServerConnection(port, playerMoveQueueForRemote, opponentMoveQueue, playerMap, opponentMap);
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

        conn = new TCPServerConnection(port, playerMoveQueue, opponentMoveQueue, playerMap, opponentMap);
    }

    @Override
    protected Boolean playerShooting() {
        try {
            while(playerMoveQueue.isEmpty()) {
                //wait until player performs a move
            }
            Field f = playerMoveQueue.remove();
            opponentMap.shootAt(f);
            playerMoveQueueForRemote.add(f);
            return f.isAttacked();
        } catch (OutsideOfMapPlacementException ex) {
            Logger.getLogger(PlayerGuestedGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    protected Boolean opponentShooting() {
        try {
            while (opponentMoveQueue.isEmpty()) {
                //wait until remote player performs a move
            }
            Field f = opponentMoveQueue.remove();
            playerMap.shootAt(f);
            return f.isAttacked();
        } catch (OutsideOfMapPlacementException ex) {
            Logger.getLogger(PlayerGuestedGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new UnsupportedOperationException("TODO");
    }
}
