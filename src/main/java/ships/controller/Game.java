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

    public Game() {
        this.state = State.DEPLOYMENT;

        this.playerMap = new GameMap();
        this.opponentMap = new GameMap();

        this.playerMapView = new PlayerMapView(playerMap);
        this.opponentMapView = new OpponentMapView(opponentMap);

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
    protected State getState() {
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

    public abstract void startPlacement(Ship.Size size);

    public abstract void stopPlacement();

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

    enum State {
        DEPLOYMENT, BATTLE
    }

    enum NextMove {
        PLAYER, OPPONENT
    }

    protected State state = State.DEPLOYMENT;
    protected NextMove nextMove = NextMove.PLAYER;

    protected Map playerMap;
    protected Map opponentMap;

    protected PlayerMapView playerMapView;
    protected OpponentMapView opponentMapView;

    protected Queue<Boolean> playerMoveQueue = new ConcurrentLinkedQueue<>();
    protected Queue<Boolean> opponentMoveQueue = new ConcurrentLinkedQueue<>();

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
            while (playerMap.getScore() > 0 && opponentMap.getScore() > 0) {
                if (nextMove == NextMove.PLAYER) {
                    boolean isHit = playerShooting();
                    if (isHit) {
                        nextMove = NextMove.PLAYER;
                    } else {
                        nextMove = NextMove.OPPONENT;
                    }
                } else {
                    boolean isHit = opponentShooting();
                    if (isHit) {
                        nextMove = NextMove.OPPONENT;
                    } else {
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
     *
     * @return <b>true</b> if enemy ship has been hit, <b>false</b> otherwise
     */
    protected Boolean playerShooting() {
        while (playerMoveQueue.isEmpty()) {
            //wait until player performs a move
        }
        return playerMoveQueue.remove();
    }

    /**
     * Wait for enemy's move
     *
     * @return <b>true</b> if enemy hit player's ship, <b>false</b> otherwise
     */
    protected abstract Boolean opponentShooting();


    private class ClickObserver implements MapClickObserver {

        @Override
        public void fieldClickedEvent(FieldSelectEvent fce, MapView bm) {

            if (fce.getButton() != FieldSelectEventImpl.BUTTON1) {
                return;
            }

            if (
                    getState() != State.BATTLE &&
                            playerMap.isDeploymentFinished() &&
                            opponentMap.isDeploymentFinished()
                    ) {
                setState(State.BATTLE);
            }

            if (nextMove == NextMove.PLAYER) {
                try {
                    if (opponentMap.getField(fce.getRow(), fce.getCol()).isAttacked()) {
                        return;
                    }
                    Boolean result = opponentMap.shootAt(new FieldImpl(fce.getRow(), fce.getCol()));
                    playerMoveQueue.add(result);
                } catch (OutsideOfMapPlacementException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            opponentMapView.showHitsOnMap();
            playerMapView.showShipsOnMap();
        }
    }
}
