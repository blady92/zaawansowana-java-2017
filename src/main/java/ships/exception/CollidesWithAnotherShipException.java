/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.exception;

import ships.model.Field;

import java.util.ArrayList;
import java.util.List;

public class CollidesWithAnotherShipException extends Exception {
    
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
 specified detail message and collisions.
     *
     * @param msg the detail message.
     * @param collisions
     */
    public CollidesWithAnotherShipException(String msg, List<Field> collisions) {
        super(msg);
        this.collisions = collisions;
    }
}
