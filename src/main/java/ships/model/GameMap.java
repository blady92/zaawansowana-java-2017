package ships.model;

import ships.exception.ShipPlacementNotPossibleException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mateusz Kozlowski
 */
public class GameMap implements Map
{
    private static final int mapSize = 10;
    private Field[][] map;
    private List<Ship> ships;
    private HashMap<ShipImpl.Size, Integer> availableShips;

    public GameMap() {
        map = new Field[mapSize][mapSize];
        availableShips = new HashMap<>();
        availableShips.put(ShipImpl.Size.ONE, 4);
        availableShips.put(ShipImpl.Size.TWO, 3);
        availableShips.put(ShipImpl.Size.THREE, 2);
        availableShips.put(ShipImpl.Size.FOUR, 1);

        ships = new ArrayList<>();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = new FieldImpl(FieldImpl.State.Empty, i, j);
            }
        }
    }

    public void placeShip(Field startField, Boolean direction, ShipImpl.Size shipSize) throws ShipPlacementNotPossibleException {
        throw new RuntimeException("Not implemented");
    }

    public Boolean shootAt(Field position) {
        throw new RuntimeException("Not implemented");
    }
}
