package ships.controller;

import ships.exception.OutsideOfMapPlacementException;
import ships.model.*;
import ships.view.*;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Game {

    public Game(Map playerMap, Map opponentMap, PlayerMapView playerMapView, OpponentMapView opponentMapView) {
        this.state = State.DEPLOYMENT;

        this.playerMap = playerMap;
        this.opponentMap = opponentMap;

        this.playerMapView = playerMapView;
        this.opponentMapView = opponentMapView;

        this.opponentMapView.addFieldSelectObserver(new ClickObserver());
    }

    public Game(PlayerMapView playerMapView, OpponentMapView opponentMapView) {
        this.state = State.DEPLOYMENT;

        this.playerMap = new GameMap();
        this.opponentMap = new GameMap();

        this.playerMapView = playerMapView;
        this.opponentMapView = opponentMapView;

        this.opponentMapView.addFieldSelectObserver(new ClickObserver());
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @return the playerMapView
     */
    public MapView getPlayerMapView() {
        return this.playerMapView;
    }

    /**
     * @return the opponentMapView
     */
    public MapView getOpponentMapView() {
        return this.opponentMapView;
    }

    /**
     * @return the playerMap
     */
    @Deprecated
    public Map getPlayerMap() {
        return playerMap;
    }

    /**
     * @return the opponentMap
     */
    @Deprecated
    public Map getOpponentMap() {
        return opponentMap;
    }

    public void startPlacement(Ship.Size size) {
        playerMapView.startPlacement(size);
    }

    public void stopPlacement() {
        playerMapView.stopPlacement();
    }

    /**
     * @param state the state to set
     */
    protected void setState(State state) {
        this.state = state;
        if (state == State.BATTLE) {
            gameLoop.start();
        }
    }

    /**
     * @param playerMapView the playerMapView to set
     */
    public void setPlayerMapView(PlayerMapView playerMapView) {
        this.playerMapView = playerMapView;
    }

    /**
     * @param opponentMapView the opponentMapView to set
     */
    public void setOpponentMapView(OpponentMapView opponentMapView) {
        this.opponentMapView = opponentMapView;
    }

    public enum State {
        CONNECTING, DEPLOYMENT, WAITING, BATTLE
    }

    enum NextMove {
        PLAYER, OPPONENT
    }

    public volatile State state = State.DEPLOYMENT;
    protected NextMove nextMove = NextMove.PLAYER;

    protected Map playerMap;
    protected Map opponentMap;

    protected PlayerMapView playerMapView;
    protected OpponentMapView opponentMapView;

    protected volatile Queue<Field> playerMoveQueue = new ConcurrentLinkedQueue<>();
    protected volatile Queue<Field> opponentMoveQueue = new ConcurrentLinkedQueue<>();

    public boolean isDeploymentFinished() {
        return getState() != State.DEPLOYMENT;
    }

    public Integer getPlayerScore() {
        return playerMap.getScore();
    }

    public Integer getOpponentScore() {
        return opponentMap.getScore();
    }

    public Integer getAvailableShipCount(Ship.Size size) {
        return playerMap.getAvailableShipCount(size);
    }

    private Thread gameLoop = new Thread(new Runnable() {

        @Override
        public void run() {
            while(playerMap.getScore() > 0 && opponentMap.getScore() > 0) {
                if (nextMove == NextMove.PLAYER) {
                    boolean isHit = playerShooting();
                    if (isHit) {
                        nextMove = NextMove.PLAYER;
                    }
                    else {
                        nextMove = NextMove.OPPONENT;
                    }
                }
                else {
                    boolean isHit = opponentShooting();
                    if (isHit) {
                        nextMove = NextMove.OPPONENT;
                    }
                    else {
                        nextMove = NextMove.PLAYER;
                    }
                }
            }
            if (playerMap.getScore() == 0) {
                JOptionPane.showMessageDialog(null, "You lost!", "Game over", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String nickname = JOptionPane.showInputDialog(null, "Type your nickname to get to the high score list:", "You won!", JOptionPane.QUESTION_MESSAGE);
                if (nickname != null) {
                    try {
                        sendScoreToServer(nickname, playerMap.getScore());
                    } catch (ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "SQLite class not found. Contact the application vendor", "Error!", JOptionPane.ERROR_MESSAGE, null);
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "There was unexpected error during sending your score. Sorry", "Error!", JOptionPane.ERROR_MESSAGE, null);
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            nextMove = NextMove.OPPONENT;
        }
    });

    private void sendScoreToServer(String nickname, Integer score) throws ClassNotFoundException, SQLException {
        SqliteDao dao = new SqliteDao();
        dao.addNewScore(nickname, score);
    }

    /**
     * Wait for player's move
     * @return <b>true</b> if enemy ship has been hit, <b>false</b> otherwise
     */
    protected Boolean playerShooting() {
        while(playerMoveQueue.isEmpty()) {
            //wait until player performs a move
        }
        return playerMoveQueue.remove().isAttacked();
    }

    /**
     * Wait for enemy's move
     * @return <b>true</b> if enemy hit player's ship, <b>false</b> otherwise
     */
    protected abstract Boolean opponentShooting();


    public class ClickObserver implements MapClickObserver {

        @Override
        public void fieldClickedEvent(FieldSelectEvent fce, MapView bm) {

            if (
                    getState() != State.BATTLE &&
                            playerMap.isDeploymentFinished() &&
                            opponentMap.isDeploymentFinished()
                    ) {
                setState(State.BATTLE);
            }

            if (fce.getButton() != FieldSelectEventImpl.BUTTON1) {
                return;
            }

            /*if (state == State.DEPLOYMENT && playerMap.isDeploymentFinished()) {
                setState(State.WAITING);
            }*/

            if (getState() != State.BATTLE) {
                return;
            }

            if (nextMove == NextMove.PLAYER) {
                try {
                    if (opponentMap.getField(fce.getRow(), fce.getCol()).isAttacked()) {
                        return;
                    }
                    Field f = new FieldImpl(fce.getRow(), fce.getCol());
                    Boolean result = opponentMap.shootAt(f);
                    if (result) {
                        f.attack();
                    }
                    playerMoveQueue.add(f);
                } catch (OutsideOfMapPlacementException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            opponentMapView.showHitsOnMap();
            playerMapView.showShipsOnMap();
        }

    };
}
