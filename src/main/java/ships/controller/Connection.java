/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author r4pt0r
 */
public abstract class Connection {

    protected ObjectMapper mapper;
    protected Socket sock;
    protected InputStream is;
    protected OutputStream os;

    public Connection() {
        mapper = new ObjectMapper();
        //mapper.enableDefaultTyping();
    }

    /**
     * Send data through the connection
     * @param packet to send
     * @throws IOException
     */
    public void sendPacket(CommunicationPacket packet) throws IOException {
        this.serializePacket(packet, os);
    }

    /**
     * Receive data from the connection
     * @return data packet
     */
    public CommunicationPacket receivePacket() throws IOException {
        return this.deserializePacket(is);
    }

    /**
     * Serialize packet to JSON and write to OutputStream
     * @param packet
     * @param os
     * @throws IOException
     */
    protected void serializePacket(CommunicationPacket packet, OutputStream os) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mapper.writeValue(baos, packet);
        os.write(baos.toByteArray());
    }

    /**
     * Deserialize packet from JSON in InputStream and return as packet class
     * @param is
     * @return
     * @throws IOException
     */
    protected CommunicationPacket deserializePacket(InputStream is) throws IOException {
        StringBuilder sb =new StringBuilder();
        do {
            byte[] buffer = new byte[is.available()];
            if (is.available() == 0) {
                continue;
            }
            int readCnt = is.read(buffer);
            if (readCnt == 0) {
                continue;
            }
            String str = new String(buffer, 0, readCnt);
            sb.append(str);
        }
        while(!JSONUtils.checkValidity(sb.toString()));
        return mapper.readValue(sb.toString(), CommunicationPacket.class);
    }
}
