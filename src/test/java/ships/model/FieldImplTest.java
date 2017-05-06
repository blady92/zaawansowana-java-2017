package ships.model;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junitparams.JUnitParamsRunner.$;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class FieldImplTest {

    @Test
    @Parameters
    public void shouldReturnValidComparisonResult(Field first, Field second, boolean expected) {
        //given
        //when
        //then
        if (expected) {
            assertTrue(first.equals(second));
        } else {
            assertFalse(first.equals(second));
        }
    }

    public Object[] parametersForShouldReturnValidComparisonResult() {
        return $(
                $(new FieldImpl(Field.State.EMPTY, 0, 0), new FieldImpl(Field.State.EMPTY, 0, 0), true),
                $(new FieldImpl(Field.State.EMPTY, 0, 1), new FieldImpl(Field.State.EMPTY, 0, 0), false),
                $(new FieldImpl(Field.State.EMPTY, 1, 0), new FieldImpl(Field.State.EMPTY, 0, 0), false),
                $(new FieldImpl(Field.State.SHIP, 0, 0), new FieldImpl(Field.State.EMPTY, 0, 0), false)
        );
    }

}