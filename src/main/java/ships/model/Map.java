package ships.model;

import ships.exception.CollidesWithAnotherShipException;
import ships.exception.NoShipsAvailableException;
import ships.exception.OutsideOfMapPlacementException;

/**
 * @author Mateusz Kozlowski
 */
interface Map {

    /**
     * Places ship at given position
     *
     * @param ship
     * @throws CollidesWithAnotherShipException
     * @throws NoShipsAvailableException
     * @throws OutsideOfMapPlacementException
     */
    void placeShip(Ship ship) throws CollidesWithAnotherShipException, NoShipsAvailableException, OutsideOfMapPlacementException;

    /**
     * Performs a shoot at position given
     *
     * @param position position to shoot at
     * @return <b>true</b> if hit, <b>false</b> otherwise
     */
    Boolean shootAt(Field position) throws OutsideOfMapPlacementException;

    /**
     * Gets number of ships of size given user can still place on map
     *
     * @param size
     * @return
     */
    Integer getAvailableShipCount(Ship.Size size);

    /**
     * Gets field data at position given
     *
     * @param row
     * @param col
     * @return
     */
    Field getField(Integer row, Integer col);
}
