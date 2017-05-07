package ships.model;

import ships.exception.CollidesWithAnotherShipException;
import ships.exception.NoShipsAvailableException;
import ships.exception.OutsideOfMapPlacementException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import ships.exception.ShipNotFoundException;

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
                map[i][j] = new FieldImpl(Field.State.EMPTY, i, j);
            }
        }
    }

    /**
     * Places ship at given position
     *
     * @param ship
     * @throws CollidesWithAnotherShipException
     * @throws NoShipsAvailableException
     * @throws OutsideOfMapPlacementException
     */
    public void placeShip(Ship ship)
            throws CollidesWithAnotherShipException, NoShipsAvailableException, OutsideOfMapPlacementException {
        if (!checkShipValidity(ship)) {
            throw new OutsideOfMapPlacementException();
        }
        decrementAvailabelShipCount(ship.getSize());
        ships.add(ship);
        List<Field> fields = isAbleToPlaceShip(ship);
        if (!fields.isEmpty()) {
            throw new CollidesWithAnotherShipException(fields);
        }
        markPositionOnMap(ship);
    }

    /**
     * Check if ship position is within the map
     *
     * @param ship
     * @return
     */
    private Boolean checkShipValidity(Ship ship) {
        Integer r = ship.getPosition().getRow();
        Integer c = ship.getPosition().getCol();
        Integer end = ship.getSize().getSize() - 1;
        if (ship.getDirection() == Ship.Direction.VERTICAL) {
            end += r;
        } else {
            end += c;
        }
        if (r < 0
                || r >= mapSize
                || c < 0
                || c >= mapSize
                || end >= mapSize) {
            return false;
        }
        return true;
    }

    /**
     * Checks if it is possible to place ship at given position
     *
     * @param ship
     * @return conflicted field location array
     */
    public List<Field> isAbleToPlaceShip(Ship ship) {

        List<Field> conflicts = new ArrayList<>();
        int r = ship.getPosition().getRow();
        int c = ship.getPosition().getCol();
        Integer length = ship.getSize().getSize();

        if (ship.getDirection() == Ship.Direction.VERTICAL) {
            Integer col = ship.getPosition().getCol();
            for (int row = r; row < r + length; row++) {
                if (map[row][col].getState() != Field.State.EMPTY) {
                    conflicts.add(map[row][col]);
                }
            }
        } else {
            Integer row = ship.getPosition().getRow();
            for (int col = c; col < c + length; col++) {
                if (map[row][col].getState() != Field.State.EMPTY) {
                    conflicts.add(map[row][col]);
                }
            }
        }
        return conflicts;
    }

    /**
     * Performs a shoot at position given
     *
     * @param position position to shoot at
     * @return <b>true</b> if hit, <b>false</b> otherwise
     */
    public Boolean shootAt(Field position) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Gets number of ships of size given user can still place on map
     *
     * @param size
     * @return
     */
    public Integer getAvailableShipCount(Ship.Size size) {
        return availableShips.get(size);
    }

    public List<Ship> getShips() {
        return ships;
    }

    private void decrementAvailabelShipCount(Ship.Size size) throws NoShipsAvailableException {
        Integer count = availableShips.get(size);
        if (count < 1) {
            throw new NoShipsAvailableException();
        }
        count--;
        availableShips.put(size, count);
    }

    /**
     * Gets field data at position given
     *
     * @param row
     * @param col
     * @return
     */
    public Field getField(Integer row, Integer col) {
        return map[row][col];
    }

    /**
     * Gets score counted as number of ship segments that hasn't been hit
     *
     * @return
     */
    public Integer getScore() {
        throw new RuntimeException("Not implemented");
    }

    private void markPositionOnMap(Ship ship)
            throws OutsideOfMapPlacementException {
        Bounds bounds = new Bounds(ship);

        markForbiddenAreaOnMap(bounds, ship);
        markShipPositionOnMap(ship);
    }

    private void markShipPositionOnMap(Ship ship) {

        int r = ship.getPosition().getRow();
        int c = ship.getPosition().getCol();
        Integer length = ship.getSize().getSize();

        if (ship.getDirection() == Ship.Direction.VERTICAL) {
            Integer col = ship.getPosition().getCol();
            for (int row = r; row < r + length; row++) {
                map[row][col].setState(Field.State.SHIP);
            }
        } else {
            Integer row = ship.getPosition().getRow();
            for (int col = c; col < c + length; col++) {
                map[row][col].setState(Field.State.SHIP);
            }
        }
    }

    private void markForbiddenAreaOnMap(Bounds bounds, Ship ship) {
        for (int across = bounds.firstForbiddenAcross; across <= bounds.lastForbiddenAcross; across++) {
            for (int along = bounds.firstForbiddenAlong; along <= bounds.lastForbiddenAlong; along++) {
                if (ship.getDirection() == Ship.Direction.VERTICAL) {
                    map[across][along].setState(Field.State.FORBIDDEN);
                } else {
                    map[along][across].setState(Field.State.FORBIDDEN);
                }
            }
        }
    }

    public Ship getShipAtPosition(Field position)
            throws ShipNotFoundException {
        for (Ship s : ships) {
            Integer rs = s.getPosition().getRow();
            Integer re = s.getPosition().getRow();
            Integer cs = s.getPosition().getCol();
            Integer ce = s.getPosition().getCol();
            if (s.getDirection() == Ship.Direction.VERTICAL) {
                re += s.getSize().getSize();
            } else {
                ce += s.getSize().getSize();
            }
            if (position.getRow() >= rs
                    && position.getRow() <= re
                    && position.getCol() >= cs
                    && position.getCol() <= ce) {
                return s;
            }
        }
        throw new ShipNotFoundException();
    }

    private static class Bounds {

        int firstForbiddenAcross, lastForbiddenAcross, firstForbiddenAlong, lastForbiddenAlong;

        public Bounds(Ship ship) {

            int r = ship.getPosition().getRow();
            int c = ship.getPosition().getCol();
            Integer length = ship.getSize().getSize();

            if (ship.getDirection() == Ship.Direction.VERTICAL) {
                //mark adjacent positions as forbidden
                firstForbiddenAcross = r - 1;
                lastForbiddenAcross = r + length;
                firstForbiddenAlong = c - 1;
                lastForbiddenAlong = c + 1;
            } else {
                //mark adjacent positions as forbidden
                firstForbiddenAcross = c - 1;
                lastForbiddenAcross = c + length;
                firstForbiddenAlong = r - 1;
                lastForbiddenAlong = r + 1;
            }

            fixMarkingBounds();

        }

        private void fixMarkingBounds() {

            if (firstForbiddenAcross < 0) {
                firstForbiddenAcross = 0;
            }
            if (firstForbiddenAlong < 0) {
                firstForbiddenAlong = 0;
            }
            if (lastForbiddenAcross > mapSize - 1) {
                lastForbiddenAcross = mapSize - 1;
            }
            if (lastForbiddenAlong > mapSize - 1) {
                lastForbiddenAlong = mapSize - 1;
            }
        }

    }
}
