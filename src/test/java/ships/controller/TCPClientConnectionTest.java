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

/**
 *
 * @author r4pt0r
 */
public class TCPClientConnectionTest {

    public TCPClientConnectionTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
    }

    @Mock
    Socket sock;

    @InjectMocks
    TCPClientConnection sut = new TCPClientConnection(sock);

    @Test
    public void shouldSendAndReceiveMovePacket() throws IOException {
        //given
        String expected = "{"
                + "\"field\":["
                + "\"ships.model.FieldImpl\",{"
                + "\"attacked\":false,"
                + "\"state\":\"EMPTY\","
                + "\"row\":1,\"col\":2," + "\"shipHere\":false"
                + "}]}";
        Field f = new FieldImpl(1, 2);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteArrayInputStream bais;
        when(sock.getOutputStream()).thenReturn(baos);
        when(sock.getInputStream()).thenReturn(null);
        MovePacket mp = new MovePacket(new FieldImpl(1, 2));
        //when
        sut.sendPacket(mp);
        String s = baos.toString();/////////////////////////////////////////////
//        bais = new ByteArrayInputStream(baos.toByteArray());
//        MovePacket packet = (MovePacket) sut.receivePacket();
//        Field receivedField = packet.getField();
        //then
        assertEquals(expected, s);
//        assertEquals(f, receivedField);
    }

}
