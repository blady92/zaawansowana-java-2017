package ships.controller;

import org.junit.*;
import ships.model.FieldImpl;
import ships.model.Ship;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MapPacketTest {

    public MapPacketTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldGetValidMapPacket() {
        //given
        List<Ship> ships = new ArrayList<>();
        ships.add(new Ship(Ship.Size.ONE, new FieldImpl(0, 1), Ship.Direction.VERTICAL));
        //when
        MapPacket packet = new MapPacket(ships);
        //then
        assertEquals(packet.getShips().get(0).getPosition(), new FieldImpl(0, 1));
        assertEquals(packet.getShips().get(0).getDirection(), Ship.Direction.VERTICAL);
        assertEquals(packet.getShips().get(0).getSize(), Ship.Size.ONE);
    }

}