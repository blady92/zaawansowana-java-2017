package ships.controller;

import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ships.exception.ShipGameException;
import ships.view.OpponentMapView;
import ships.view.PlayerMapView;

import static org.junit.Assert.assertTrue;

public class PlayerVsComputerGameTest {

    public PlayerVsComputerGameTest() {
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
    PlayerMapView mockedPlayerMapView;

    @Mock
    OpponentMapView mockedOpponentMapView;

    @InjectMocks
    PlayerVsComputerGame sut;

    @Test
    public void shouldRandomlyPlaceComputerShips() throws ShipGameException {
        //given
        //when
        //then
        assertTrue(sut.getOpponentScore() == 20);
    }

}