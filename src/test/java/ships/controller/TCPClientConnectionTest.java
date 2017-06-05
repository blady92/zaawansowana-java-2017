/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ships.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import ships.model.Field;
import ships.model.FieldImpl;
import ships.model.Ship;

/**
 *
 * @author r4pt0r
 */
public class TCPClientConnectionTest {

    @Mock
    Socket sock;

    @InjectMocks
    TCPClientConnection sut = new TCPClientConnection(sock);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

   /*@Test
    public void shouldSendAndReceiveMovePacket() throws IOException {
        //given
        Field f = new Field(1, 2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais;
        when(sock.getOutputStream()).thenReturn(baos);
        MovePacket mp = new MovePacket(new Field(1, 2));
        //when
        sut.sendPacket(mp);
        String s = baos.toString();/////////////////////////////////////////////
        bais = new ByteArrayInputStream(baos.toByteArray());
        when(sock.getInputStream()).thenReturn(bais);
        MovePacket packet = (MovePacket) sut.receivePacket();
        Field receivedField = packet.getField();
        //then
        assertEquals(f, receivedField);
    }

    @Test
    public void shouldSendAndReceiveMapPacket() throws IOException {
        //given
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(Ship.Size.FOUR, new Field(1, 2), Ship.Direction.VERTICAL));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais;
        when(sock.getOutputStream()).thenReturn(baos);
        MapPacket packet = new MapPacket(ships);
        //when
        sut.sendPacket(packet);
        String s = baos.toString();/////////////////////////////////////////////
        bais = new ByteArrayInputStream(baos.toByteArray());
        when(sock.getInputStream()).thenReturn(bais);
        MapPacket received = (MapPacket) sut.receivePacket();
        List<Ship> receivedShips = packet.getShips();
        //then
        assertEquals(ships, receivedShips);
    }*/

}
