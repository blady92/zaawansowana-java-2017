package ships.model;

import org.junit.Before;
import org.junit.Test;
import ships.exception.CollidesWithAnotherShipException;
import ships.exception.ShipGameException;

import java.util.ArrayList;
import java.util.List;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.*;

public class GameMapTest {

    private GameMap objectUnderTest;

    @Before
    public void setUp() {
        objectUnderTest = new GameMap();
    }

    @Test
    public void shouldPlaceShipVerticallyOnMap() throws ShipGameException {
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
    public void shouldPlaceShipHorizontallyOnMap() throws ShipGameException {
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
            throws ShipGameException {
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
            throws ShipGameException {
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

    @Test
    public void shouldReturnScoreAtStart() throws ShipGameException {
        //given
        objectUnderTest.placeShip(new Ship(Ship.Size.FOUR, new FieldImpl(0, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.THREE, new FieldImpl(2, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.THREE, new FieldImpl(4, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.TWO, new FieldImpl(6, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.TWO, new FieldImpl(6, 3), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.TWO, new FieldImpl(8, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.ONE, new FieldImpl(8, 3), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.ONE, new FieldImpl(8, 5), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.ONE, new FieldImpl(8, 7), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.ONE, new FieldImpl(8, 9), Ship.Direction.HORIZONTAL));
        //when
        //then
        assertTrue(objectUnderTest.isDeploymentFinished());
        assertEquals(20, objectUnderTest.getScore());
    }

    @Test
    public void shouldHitTheShipAndReturnValidScore() throws ShipGameException {
        //given
        objectUnderTest.placeShip(new Ship(Ship.Size.FOUR, new FieldImpl(0, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.THREE, new FieldImpl(2, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.THREE, new FieldImpl(4, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.TWO, new FieldImpl(6, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.TWO, new FieldImpl(6, 3), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.TWO, new FieldImpl(8, 0), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.ONE, new FieldImpl(8, 3), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.ONE, new FieldImpl(8, 5), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.ONE, new FieldImpl(8, 7), Ship.Direction.HORIZONTAL));
        objectUnderTest.placeShip(new Ship(Ship.Size.ONE, new FieldImpl(8, 9), Ship.Direction.HORIZONTAL));
        //when
        objectUnderTest.shootAt(new FieldImpl(8, 7));
        //then
        assertTrue(objectUnderTest.getField(8, 7).isAttacked());
        assertTrue(objectUnderTest.getShipAtPosition(new FieldImpl(8, 7)).isSunken());
        assertEquals(19, objectUnderTest.getScore());
    }

    @Test
    public void shouldGetShipAtPosition() throws ShipGameException {
        //given
        Ship ship = new Ship(Ship.Size.FOUR, new FieldImpl(0, 0), Ship.Direction.HORIZONTAL);
        objectUnderTest.placeShip(ship);
        //when
        Ship actual = objectUnderTest.getShipAtPosition(new FieldImpl(0, 3));
        //then
        assertSame(ship, actual);
    }
}
