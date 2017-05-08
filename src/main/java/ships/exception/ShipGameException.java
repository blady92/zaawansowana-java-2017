/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.exception;

/**
 *
 * @author Kamil Lorenc
 */
public class ShipGameException extends Exception {

    /**
     * Creates a new instance of <code>ShipGameException</code> without detail
     * message.
     */
    public ShipGameException() {
    }

    /**
     * Constructs an instance of <code>ShipGameException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ShipGameException(String msg) {
        super(msg);
    }
}
