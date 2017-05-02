package ships.model;

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
     * @return <b>true</b> if ship placement was possible, <b>false</b> otherwise
     */
    Boolean placeShip(FieldImpl startField, Boolean direction, ShipImpl.Size shipSize);

    /**
     * Performs a shoot at position given
     * @param position position to shoot at
     * @return <b>true</b> if hit, <b>false</b> otherwise
     */
    Boolean shootAt(FieldImpl position);
}
