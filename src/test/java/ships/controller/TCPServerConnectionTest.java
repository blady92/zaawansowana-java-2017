package ships.controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import ships.model.Field;
import ships.model.FieldImpl;
import ships.model.Map;
import ships.model.Ship;

/**
 *
 * @author r4pt0r
 */
public class TCPServerConnectionTest {

    public TCPServerConnectionTest() {
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

    Queue<Field> playerMoveQueue = new ConcurrentLinkedQueue<>();
    Queue<Field> opponentMoveQueue = new ConcurrentLinkedQueue<>();

    @Mock
    ServerSocket srv;

    @Mock
    Socket sock;

    @Mock
    Map playerMap;

    @Mock
    Map opponentMap;

    @Mock
    InputStream is;

    @Mock
    OutputStream os;

//    @InjectMocks
//    TCPServerConnection sut;// = new TCPServerConnection(srv, sock, playerMoveQueue, opponentMoveQueue, playerMap, opponentMap);

    /*@Test
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

    /*@Test
    public void shouldFillMoveQueue() throws IOException {
        //given
        byte buffer[] = new byte[1024];
        when(sock.getOutputStream()).thenReturn(os);
        when(sock.getInputStream()).thenReturn(is);
        ArgumentCaptor<byte[]> captor = ArgumentCaptor.forClass(byte[].class);
        verify(os).write(captor.capture());
        //when
        playerMoveQueue.add(new Field(1, 2));
        captor.getValue();
        //then
        fail("Write test body");
    }*/

    @Test
    public void shouldSendAndReceiveMovePacket() throws IOException {
        //given
        when(sock.getOutputStream()).thenReturn(os);
        when(sock.getInputStream()).thenReturn(is);
        TCPServerConnection sut = new TCPServerConnection(srv, sock, playerMoveQueue, opponentMoveQueue, playerMap, opponentMap);
        ArgumentCaptor<byte[]> outCaptor = ArgumentCaptor.forClass(byte[].class);
        ArgumentCaptor<byte[]> inCaptor = ArgumentCaptor.forClass(byte[].class);
        //when
        sut.sendPacket(new MovePacket(new FieldImpl(1, 2)));
        verify(os).write(outCaptor.capture());
        byte[] outBuf = outCaptor.getValue();
        /*when(is.read((byte[]) any())).thenReturn(outBuf.length);
        CommunicationPacket packet = sut.receivePacket();
        verify(is).read(inCaptor.capture());
        byte[] inBuf = inCaptor.getValue();
        if (inBuf.length > outBuf.length) {
            System.arraycopy(outBuf, 0, inBuf, 0, outBuf.length);
        }*/

        //then
        assertTrue(JSONUtils.checkValidity(new String(outBuf)));
    }

}
