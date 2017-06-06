package ships.controller;

import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ships.exception.ShipGameException;
import ships.model.Field;
import ships.model.FieldImpl;
import ships.model.Map;
import ships.view.FieldSelectEventImpl;
import ships.view.OpponentMapView;
import ships.view.PlayerMapView;

import java.io.IOException;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerGuestedGameTest {

    public PlayerGuestedGameTest() {
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
    PlayerMapView playerMapView;

    @Mock
    OpponentMapView opponentMapView;

    @Mock
    Map playerMap;

    @Mock
    Map opponentMap;

    @Mock
    TCPClientConnection clientConnection;

    @Test
    public void shouldPerformFewMovesOnAMap() throws ShipGameException, IOException, InterruptedException {
        //given
        Queue<Field> playerQueue;
        Queue<Field> opponentQueue;
        when(playerMap.getScore()).thenReturn(20);
        when(opponentMap.getScore()).thenReturn(20);
        when(opponentMap.getField(1, 2)).thenReturn(new FieldImpl(1, 2));
        ArgumentCaptor<Game.ClickObserver> captor = ArgumentCaptor.forClass(Game.ClickObserver.class);
        ArgumentCaptor<Queue> playerQueueCaptor = ArgumentCaptor.forClass(Queue.class);
        ArgumentCaptor<Queue> opponentQueueCaptor = ArgumentCaptor.forClass(Queue.class);
        ArgumentCaptor<Field> shootCaptor = ArgumentCaptor.forClass(Field.class);
        PlayerGuestedGame sut = new PlayerGuestedGame(playerMap, opponentMap, playerMapView, opponentMapView, clientConnection);
        verify(opponentMapView).addFieldSelectObserver(captor.capture());
        verify(clientConnection).setPlayerMoveQueue(playerQueueCaptor.capture());
        verify(clientConnection).setOpponentMoveQueue(opponentQueueCaptor.capture());
        playerQueue = playerQueueCaptor.getValue();
        opponentQueue = opponentQueueCaptor.getValue();
        sut.setState(Game.State.BATTLE);
        //when
        opponentQueue.add(new FieldImpl(2, 3));
        Thread.sleep(1000);
        verify(playerMap).shootAt(shootCaptor.capture());
        Field f = shootCaptor.getValue();
        Game.ClickObserver clickObserver = captor.getValue();
        clickObserver.fieldClickedEvent(new FieldSelectEventImpl(1, 2, FieldSelectEventImpl.BUTTON1), opponentMapView);
        Thread.sleep(1000);
        //then
        assertEquals(new FieldImpl(1, 2), playerQueue.remove());
        assertEquals(new FieldImpl(2, 3), f);
    }

}