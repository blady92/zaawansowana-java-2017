package ships.exception;

public class NoShipsAvailableException extends ShipGameException {

    /**
     * Creates a new instance of <code>NoShipsAvailableException</code> without
     * detail message.
     */
    public NoShipsAvailableException() {
    }

    /**
     * Constructs an instance of <code>NoShipsAvailableException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoShipsAvailableException(String msg) {
        super(msg);
    }
}
