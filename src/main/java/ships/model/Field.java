package ships.model;

/**
 * @author Mateusz Kozlowski
 */
public interface Field {

    enum State {
        EMPTY, SHIP, FORBIDDEN
    }

    /**
     * Informs if field is occuped by a ship
     *
     * @return
     */
    Boolean isShipHere();

    /**
     * Informs if field had been already shot by opponent
     *
     * @return <b>true</b> if field had been attacked
     */
    Boolean isAttacked();

    int getRow();

    int getCol();

    void setState(State state);

    State getState();

    void attack();
}
