package ships.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ships.exception.CollidesWithAnotherShipException;
import ships.exception.NoShipsAvailableException;
import ships.exception.OutsideOfMapPlacementException;

import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class GameMapTest {

    private GameMap objectUnderTest;

    @Before
    public void setUp() {
        objectUnderTest = new GameMap();
    }

    @Test
    @Ignore
    //TODO: investigate
    public void shouldPlaceShipVerticallyOnMap() throws CollidesWithAnotherShipException, NoShipsAvailableException, OutsideOfMapPlacementException {
        //given
        Field startF = new FieldImpl(Field.State.EMPTY, 0, 0);
        Ship ship = new Ship(Ship.Size.TWO, startF, Ship.Direction.VERTICAL);
        List<Ship> expecteds = new ArrayList<>();
        expecteds.add(ship);
        //when
        objectUnderTest.placeShip(ship);
        Object[] fields = $(
                $(objectUnderTest.getField(0, 0), Field.State.SHIP),
                $(objectUnderTest.getField(0, 1), Field.State.FORBIDDEN),
                $(objectUnderTest.getField(0, 2), Field.State.EMPTY),
                $(objectUnderTest.getField(1, 0), Field.State.SHIP),
                $(objectUnderTest.getField(1, 1), Field.State.FORBIDDEN),
                $(objectUnderTest.getField(1, 2), Field.State.EMPTY),
                $(objectUnderTest.getField(2, 0), Field.State.FORBIDDEN),
                $(objectUnderTest.getField(2, 1), Field.State.FORBIDDEN),
                $(objectUnderTest.getField(2, 2), Field.State.EMPTY)
        );
        //then
        assertEquals((Integer) 2, objectUnderTest.getAvailableShipCount(Ship.Size.TWO));
        for (Object field : fields) {
            Object[] set = (Object[]) field;
            Field f = (Field) set[0];
            Field.State s = (Field.State) set[1];
            assertTrue(
                    "Field [" + f.getRow() + "," + f.getCol() + "] was expected to be "
                    + s.toString() + ", but is " + f.getState().toString(),
                    f.getState() == s);
        }
        assertEquals(expecteds, objectUnderTest.getShips());
    }

    @Test
    @Ignore
    //TODO: investigate
    public void shouldPlaceShipHorizontallyOnMap() throws CollidesWithAnotherShipException, NoShipsAvailableException, OutsideOfMapPlacementException {
        //given
        Field startF = new FieldImpl(Field.State.EMPTY, 0, 0);
        Ship ship = new Ship(Ship.Size.TWO, startF, Ship.Direction.HORIZONTAL);
        List<Ship> expecteds = new ArrayList<>();
        expecteds.add(ship);
        //when
        objectUnderTest.placeShip(ship);
        Object[] fields = $(
                $(objectUnderTest.getField(0, 0), Field.State.SHIP),
                $(objectUnderTest.getField(0, 1), Field.State.SHIP),
                $(objectUnderTest.getField(0, 2), Field.State.FORBIDDEN),
                $(objectUnderTest.getField(0, 3), Field.State.EMPTY),
                $(objectUnderTest.getField(1, 0), Field.State.FORBIDDEN),
                $(objectUnderTest.getField(1, 1), Field.State.FORBIDDEN),
                $(objectUnderTest.getField(1, 2), Field.State.FORBIDDEN),
                $(objectUnderTest.getField(1, 3), Field.State.EMPTY)
        );
        //then
        assertEquals((Integer) 2, objectUnderTest.getAvailableShipCount(Ship.Size.TWO));
        for (Object field : fields) {
            Object[] set = (Object[]) field;
            Field f = (Field) set[0];
            Field.State s = (Field.State) set[1];
            assertTrue(
                    "Field [" + f.getRow() + "," + f.getCol() + "] was expected to be "
                    + s.toString() + ", but is " + f.getState().toString(),
                    f.getState() == s);
        }
        assertEquals(expecteds, objectUnderTest.getShips());
    }

    @Test
    public void shouldReturnListOfConflictingFieldsWhenPlacingSecondShipOnSamePlace()
            throws CollidesWithAnotherShipException, NoShipsAvailableException, OutsideOfMapPlacementException {
        //given
        Field startF = new FieldImpl(Field.State.EMPTY, 0, 0);
        Ship ship1 = new Ship(Ship.Size.TWO, startF, Ship.Direction.VERTICAL);
        Ship ship2 = new Ship(Ship.Size.TWO, startF, Ship.Direction.HORIZONTAL);
        objectUnderTest.placeShip(ship1);
        //when
        List<Field> conflicts = objectUnderTest.isAbleToPlaceShip(ship2);
        List<Field> expected = new ArrayList<>();
        expected.add(new FieldImpl(Field.State.SHIP, 0, 0));
        expected.add(new FieldImpl(Field.State.FORBIDDEN, 0, 1));
        //then
        assertEquals(expected, conflicts);
    }

    @Test
    public void shouldRefuseToPlaceSecondShipOnSamePlace()
            throws CollidesWithAnotherShipException, NoShipsAvailableException, OutsideOfMapPlacementException {
        //given
        Field startF = new FieldImpl(Field.State.EMPTY, 0, 0);
        Ship ship1 = new Ship(Ship.Size.TWO, startF, Ship.Direction.VERTICAL);
        Ship ship2 = new Ship(Ship.Size.TWO, startF, Ship.Direction.HORIZONTAL);
        objectUnderTest.placeShip(ship1);
        //when
        List<Field> expected = new ArrayList<>();
        expected.add(new FieldImpl(Field.State.SHIP, 0, 0));
        expected.add(new FieldImpl(Field.State.FORBIDDEN, 0, 1));
        try {
            objectUnderTest.placeShip(ship2);
            fail("CollidesWithAnotherShipException expected");
        } catch (CollidesWithAnotherShipException ex) {
            //then
            assertEquals(ex.getCollisions(), expected);
        }
    }
}
