package ships.model;

import ships.exception.ShipPlacementNotPossibleException;

/**
 * @author Mateusz Kozlowski
 */
interface Map
{
    /**
     * Places ship at given position
     * @param startField indicates position at which the ship is to be placed
     * @param direction <b>true</b> if vertical, <b>false</b> otherwise
     * @param shipSize size of the ship
     * @throws ShipPlacementNotPossibleException when
     * position you want to take is already occupied
     */
    void placeShip(Field startField, Boolean direction, ShipImpl.Size shipSize) throws ShipPlacementNotPossibleException;

    /**
     * Performs a shoot at position given
     * @param position position to shoot at
     * @return <b>true</b> if hit, <b>false</b> otherwise
     */
    Boolean shootAt(Field position);
}
