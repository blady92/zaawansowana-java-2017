/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author r4pt0r
 */
public class TCPServerConnection extends Connection {

    private ServerSocket srv;
    private Socket sock;

    public TCPServerConnection(Integer port) throws IOException {
        srv = new ServerSocket(port);
        //what thread are we in? same as gui?
        sock = srv.accept();
    }

    public TCPServerConnection(ServerSocket server, Socket socket) {
        super();
        this.srv = server;
        this.sock = socket;
    }

}
