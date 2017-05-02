package ships.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mateusz Kozlowski
 */
public class GameMap implements Map
{
    private static final int mapSize = 10;
    private FieldImpl[][] map;
    private List<Ship> ships;

    public GameMap() {
        map = new FieldImpl[mapSize][mapSize];
        ships = new ArrayList<>();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = new FieldImpl(FieldImpl.State.Empty, i, j);
            }
        }
    }

    public Boolean placeShip(FieldImpl startField, Boolean direction, ShipImpl.Size shipSize) {
        throw new RuntimeException("Not implemented");
    }

    public Boolean shootAt(FieldImpl position) {
        throw new RuntimeException("Not implemented");
    }
}
