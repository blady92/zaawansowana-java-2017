package ships.exception;

import ships.model.Field;

import java.util.ArrayList;
import java.util.List;

public class CollidesWithAnotherShipException extends ShipGameException {

    private List<Field> collisions = null;

    public List<Field> getCollisions() {
        return collisions;
    }

    /**
     * Creates a new instance of <code>ShipPlacementNotPossible</code> without
     * detail message.
     */
    public CollidesWithAnotherShipException() {
        collisions = new ArrayList<>();
    }

    /**
     * Constructs an instance of <code>ShipPlacementNotPossible</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CollidesWithAnotherShipException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>ShipPlacementNotPossible</code> with the
     * specified detail message and collisions.
     *
     * @param collisions
     */
    public CollidesWithAnotherShipException(List<Field> collisions) {
        this.collisions = collisions;
    }
}
