/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author r4pt0r
 */
public abstract class Connection {

    ObjectMapper mapper;

    public Connection() {
        mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
    }

    /**
     * Send data through the connection
     *
     * @param packet to send
     * @throws IOException
     */
    abstract void sendPacket(CommunicationPacket packet) throws IOException;

    /**
     * Receive data from the connection
     *
     * @return data packet
     */
    abstract CommunicationPacket receivePacket() throws IOException;

    /**
     * Serialize packet to JSON and write to OutputStream
     *
     * @param packet
     * @param os
     * @throws IOException
     */
    protected void serializePacket(CommunicationPacket packet, OutputStream os) throws IOException {
        mapper.writeValue(os, packet);
    }

    /**
     * Deserialize packet from JSON in InputStream and return as packet class
     *
     * @param is
     * @return
     * @throws IOException
     */
    protected CommunicationPacket deserializePacket(InputStream is) throws IOException {
        return mapper.readValue(is, CommunicationPacket.class);
    }

}
