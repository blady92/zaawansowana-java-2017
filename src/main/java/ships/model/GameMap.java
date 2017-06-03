package ships.model;

import ships.exception.CollidesWithAnotherShipException;
import ships.exception.NoShipsAvailableException;
import ships.exception.OutsideOfMapPlacementException;
import ships.exception.ShipNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mateusz Kozlowski
 */
public class GameMap implements Map {

    public static final int mapSize = 10;
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
        if (!checkShipValidity(ship)) {
            if (ship.getDirection() == Ship.Direction.HORIZONTAL) {
                for (int i = ship.getPosition().getCol();
                     i < ship.getPosition().getCol() + ship.getSize().getSize()
                             && i < mapSize;
                     i++) {
                    conflicts.add(new FieldImpl(ship.getPosition().getRow(), i));
                }
            } else {
                for (int i = ship.getPosition().getRow();
                     i < ship.getPosition().getRow() + ship.getSize().getSize()
                             && i < mapSize;
                     i++) {
                    conflicts.add(new FieldImpl(i, ship.getPosition().getCol()));
                }
            }
            return conflicts;
        }
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
     * @throws ships.exception.OutsideOfMapPlacementException
     */
    public Boolean shootAt(Field position) throws OutsideOfMapPlacementException {
        Field fieldToShoot = null;
        //HACK: should be separate method with Field param
        if (!checkShipValidity(new Ship(Ship.Size.ONE, position, Ship.Direction.VERTICAL))) {
            throw new OutsideOfMapPlacementException();
        }
        for (Field[] fv : map) {
            for (Field f : fv) {
                if (f.equals(position)) {
                    fieldToShoot = f;
                    break;
                }
            }
        }
        if (fieldToShoot == null) {
            throw new RuntimeException("Position [" + position.getRow() + "," + position.getCol() + "] not found!");
        }
        try {
            this.getShipAtPosition(fieldToShoot).hitTheShip();
        } catch (ShipNotFoundException ex) {
            /* intentionally do nothing */
        }
        return fieldToShoot.attack();
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
    public int getScore() {
        if (!isDeploymentFinished()) {
            return 0;
        }
        int score = 0;
        for (Field[] fa : map) {
            for (Field f : fa) {
                if (f.isShipHere() && !f.isAttacked()) {
                    score++;
                }
            }
        }
        return score;
    }

    public boolean isDeploymentFinished() {
        for (Object v : availableShips.values()) {
            if ((Integer) v != 0) {
                return false;
            }
        }
        return true;
    }

    private void markPositionOnMap(Ship ship) {
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
                re += s.getSize().getSize() - 1;
            } else {
                ce += s.getSize().getSize() - 1;
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
