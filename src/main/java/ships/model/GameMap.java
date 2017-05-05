package ships.model;

import ships.exception.CollidesWithAnotherShipException;
import ships.exception.NoShipsAvailableException;
import ships.exception.OutsideOfMapPlacementException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mateusz Kozlowski
 */
public class GameMap implements Map {
    private static final int mapSize = 10;
    private Field[][] map;
    private List<Ship> ships;
    private HashMap<Ship.Size, Integer> availableShips;

    public GameMap() {
        map = new Field[mapSize][mapSize];
        availableShips = new HashMap<>();
        availableShips.put(Ship.Size.ONE, 4);
        availableShips.put(Ship.Size.TWO, 3);
        availableShips.put(Ship.Size.THREE, 2);
        availableShips.put(Ship.Size.FOUR, 1);

        ships = new ArrayList<>();
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                map[i][j] = new FieldImpl(FieldImpl.State.EMPTY, i, j);
            }
        }
    }

    public void placeShip(Ship ship)
            throws CollidesWithAnotherShipException, NoShipsAvailableException, OutsideOfMapPlacementException {
        int available = availableShips.get(ship.getSize());
        if (available <= 0) {
            throw new NoShipsAvailableException();
        }
        availableShips.replace(ship.getSize(), available - 1);
        int r = ship.getPosition().getRow();
        int c = ship.getPosition().getCol();
        Integer length = ship.getSizeAsInteger();
        if (c < 0 || r < 0)
            throw new OutsideOfMapPlacementException();
        if (ship.getDirection()) {
            //vertical
            if (r + length > mapSize - 1) {
                throw new OutsideOfMapPlacementException();
            }
            //mark adjacent positions as forbidden
            int firstForbiddenRow = r - 1;
            int lastForbiddenRow = r + length;
            int firstForbiddenCol = c - 1;
            int lastForbiddenCol = c + 1;
            //if sth is outside of map, fix
            if (firstForbiddenRow < 0) {
                firstForbiddenRow = 0;
            }
            if (firstForbiddenCol < 0) {
                firstForbiddenCol = 0;
            }
            if (lastForbiddenRow > mapSize - 1) {
                lastForbiddenRow = mapSize - 1;
            }
            if (lastForbiddenCol > mapSize - 1) {
                lastForbiddenCol = mapSize - 1;
            }
            for (int row = firstForbiddenRow; row <= lastForbiddenRow; row++) {
                for (int col = firstForbiddenCol; col <= lastForbiddenCol; col++) {
                    map[row][col].setState(Field.State.FORBIDDEN);
                }
            }
            Integer col = ship.getPosition().getCol();
            for (int row = r; row < r + length; row++) {
                map[row][col].setState(Field.State.SHIP);
            }
        } else {
            //horizontal
            throw new RuntimeException("Not implemented");
        }
    }

    public Boolean shootAt(Field position) {
        throw new RuntimeException("Not implemented");
    }

    public Integer getAvailableShipCount(Ship.Size size) {
        return availableShips.get(size);
    }

    public Field getField(Integer row, Integer col) {
        return map[row][col];
    }
}
