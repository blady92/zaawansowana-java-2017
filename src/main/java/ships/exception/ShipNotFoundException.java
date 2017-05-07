/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.exception;

import ships.exception.ShipGameException;

/**
 *
 * @author Kamil Lorenc
 */
public class ShipNotFoundException extends ShipGameException {

    /**
     * Creates a new instance of <code>ShipNotFoundException</code> without
     * detail message.
     */
    public ShipNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ShipNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ShipNotFoundException(String msg) {
        super(msg);
    }
}
