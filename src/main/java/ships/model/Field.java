package ships.model;

/**
 * @author Mateusz Kozlowski
 */
public interface Field
{
    /**
     * Informs if field is occuped by a ship
     * @return
     */
    Boolean isShipHere();

    /**
     * Informs if field had been already shot by opponent
     * @return <b>true</b> if field had been attacked
     */
    Boolean isAttacked();
}
