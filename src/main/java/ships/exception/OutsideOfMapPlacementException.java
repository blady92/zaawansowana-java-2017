package ships.exception;

public class OutsideOfMapPlacementException extends ShipGameException {

    /**
     * Creates a new instance of <code>OutsideOfMapPlacementException</code>
     * without detail message.
     */
    public OutsideOfMapPlacementException() {
    }

    /**
     * Constructs an instance of <code>OutsideOfMapPlacementException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public OutsideOfMapPlacementException(String msg) {
        super(msg);
    }
}
