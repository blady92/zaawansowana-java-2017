/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import ships.model.Field;
import ships.model.Ship;

/**
 *
 * @author r4pt0r
 */
public class PlayerGuestedGame extends Game {

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
        throw new UnsupportedOperationException("TODO: send the move to server");
        //return f.isAttacked();
    }

    @Override
    protected Boolean opponentShooting() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
