package ships.model;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author r4pt0r
 */
public class ShipTest {

    public ShipTest() {
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
    public void shouldReturnListOfFieldTheVerticalShipOccupies() {
        //given
        Ship ship = new Ship(Ship.Size.FOUR, new FieldImpl(1, 1), Ship.Direction.VERTICAL);
        //when
        List<Field> actual = ship.getFieldList();
        List<Field> expected = new ArrayList<>();
        expected.add(new FieldImpl(1, 1));
        expected.add(new FieldImpl(2, 1));
        expected.add(new FieldImpl(3, 1));
        expected.add(new FieldImpl(4, 1));
        //then
        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnListOfFieldTheHorizontalShipOccupies() {
        //given
        Ship ship = new Ship(Ship.Size.FOUR, new FieldImpl(1, 1), Ship.Direction.HORIZONTAL);
        //when
        List<Field> actual = ship.getFieldList();
        List<Field> expected = new ArrayList<>();
        expected.add(new FieldImpl(1, 1));
        expected.add(new FieldImpl(1, 2));
        expected.add(new FieldImpl(1, 3));
        expected.add(new FieldImpl(1, 4));
        //then
        assertEquals(expected, actual);
    }

}
