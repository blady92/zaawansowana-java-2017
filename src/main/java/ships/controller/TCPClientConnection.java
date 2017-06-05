/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author r4pt0r
 */
public class TCPClientConnection extends Connection {

    private Socket sock;

    public TCPClientConnection(String ipAddress, Integer port) throws IOException {
        sock = new Socket(ipAddress, port);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public TCPClientConnection(Socket socket) {
        super();
        this.sock = socket;
    }

}
