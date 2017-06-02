package ships.controller;

import ships.model.Map;
import ships.model.Ship;
import ships.view.MapView;

public abstract class Game {
    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @return the playerMapView
     */
    public abstract MapView getPlayerMapView();

    /**
     * @return the opponentMapView
     */
    public abstract MapView getOpponentMapView();

    /**
     * @return the playerMap
     */
    public Map getPlayerMap() {
        return playerMap;
    }

    /**
     * @return the opponentMap
     */
    public Map getOpponentMap() {
        return opponentMap;
    }

    public abstract void startPlacement(Ship.Size size);
    public abstract void stopPlacement();

    enum State {
        PLACEMENT, FIGHT
    }

    protected State state = State.PLACEMENT;

    protected Map playerMap;
    protected Map opponentMap;
}
