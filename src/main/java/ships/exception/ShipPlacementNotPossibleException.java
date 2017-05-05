package ships.exception;

/**
 * @author Mateusz Kozlowski
 */
public class ShipPlacementNotPossibleException extends Exception
{
    /**
     * Creates a new instance of <code>ShipPlacementNotPossible</code> without
     * detail message.
     */
    public ShipPlacementNotPossibleException() {
    }

    /**
     * Constructs an instance of <code>ShipPlacementNotPossible</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ShipPlacementNotPossibleException(String msg) {
        super(msg);
    }
}
