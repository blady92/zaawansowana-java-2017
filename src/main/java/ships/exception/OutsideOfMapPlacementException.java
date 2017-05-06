/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.exception;

public class OutsideOfMapPlacementException extends Exception {

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
