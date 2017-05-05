package ships.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameMapTest {
    private GameMap objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = new GameMap();
    }

    @Test
    public void shouldPlaceShipVerticallyOnMap() throws Exception {
        //given
        Field startF = new FieldImpl(Field.State.EMPTY, 0, 0);
        Ship ship = new Ship(Ship.Size.TWO, startF, true);
        //when
        objectUnderTest.placeShip(ship);
        Object[] fields = $(
                $(objectUnderTest.getField(0, 0),Field.State.SHIP),
                $(objectUnderTest.getField(0, 1),Field.State.FORBIDDEN),
                $(objectUnderTest.getField(0, 2),Field.State.EMPTY),
                $(objectUnderTest.getField(1, 0),Field.State.SHIP),
                $(objectUnderTest.getField(1, 1),Field.State.FORBIDDEN),
                $(objectUnderTest.getField(1, 2),Field.State.EMPTY),
                $(objectUnderTest.getField(2, 0),Field.State.FORBIDDEN),
                $(objectUnderTest.getField(2, 1),Field.State.FORBIDDEN),
                $(objectUnderTest.getField(2, 2),Field.State.EMPTY)
        );
        //then
        assertEquals((Integer)2, objectUnderTest.getAvailableShipCount(Ship.Size.TWO));
        for (Object field : fields) {
            Object[] set = (Object[]) field;
            Field f = (Field) set[0];
            Field.State s = (Field.State) set[1];
            assertTrue(f.getState() == s);
        }
    }
}