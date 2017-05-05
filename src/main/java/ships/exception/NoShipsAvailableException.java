/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.exception;

public class NoShipsAvailableException extends Exception {

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
