package ships.controller;

import ships.model.GameMap;
import ships.model.Ship;
import ships.view.MapView;
import ships.view.PlayerMapView;

public class PlayerVsComputerGame extends Game {

    private final PlayerMapView playerMapView;
    private final MapView opponentMapView;

    public PlayerVsComputerGame() {
        this.playerMap = new GameMap();
        this.opponentMap = new GameMap();

        this.playerMapView = new PlayerMapView(getPlayerMap());
        this.opponentMapView = new MapView();
    }

    @Override
    public void startPlacement(Ship.Size size) {
        playerMapView.startPlacement(size);
    }

    @Override
    public void stopPlacement() {
        playerMapView.stopPlacement();
    }

    @Override
    public MapView getPlayerMapView() {
        return this.playerMapView;
    }

    @Override
    public MapView getOpponentMapView() {
        return this.opponentMapView;
    }

}
