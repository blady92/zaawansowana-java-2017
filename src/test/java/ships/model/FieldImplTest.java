package ships.model;

import junitparams.JUnitParamsRunner;
import static junitparams.JUnitParamsRunner.$;
import junitparams.Parameters;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class FieldImplTest {

    public FieldImplTest() {
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
    @Parameters
    public void shouldReturnValidComparisonResult(Field first, Field second, boolean expected) {
        //given
        //when
        //then
        if (expected) {
            assertTrue(first.same(second));
        }
        else {
            assertFalse(first.same(second));
        }
    }

    public Object[] parametersForShouldReturnValidComparisonResult() {
        return $(
                $(new FieldImpl(Field.State.EMPTY, 0, 0),
                        new FieldImpl(Field.State.EMPTY, 0, 0), true),
                $(new FieldImpl(Field.State.EMPTY, 0, 1),
                        new FieldImpl(Field.State.EMPTY, 0, 0), false),
                $(new FieldImpl(Field.State.EMPTY, 1, 0),
                        new FieldImpl(Field.State.EMPTY, 0, 0), false),
                $(new FieldImpl(Field.State.SHIP, 0, 0),
                        new FieldImpl(Field.State.EMPTY, 0, 0), false)
        );
    }

}
