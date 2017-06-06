package ships.controller;

import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ships.model.Field;
import ships.model.FieldImpl;
import ships.model.Map;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    public void shouldSendAndReceiveMovePacket() throws IOException {
        //given
        when(sock.isConnected()).thenReturn(true);
        when(sock.getOutputStream()).thenReturn(os);
        when(sock.getInputStream()).thenReturn(is);
        TCPServerConnection sut = new TCPServerConnection(srv, sock, playerMoveQueue, opponentMoveQueue, playerMap, opponentMap);
        ArgumentCaptor<byte[]> outCaptor = ArgumentCaptor.forClass(byte[].class);
        //when
        sut.sendPacket(new MovePacket(new FieldImpl(1, 2)));
        verify(os).write(outCaptor.capture());
        byte[] outBuf = outCaptor.getValue();
        //then
        assertTrue(JSONUtils.checkValidity(new String(outBuf)));
    }

}
