package shiver.me.timbers.file.server.spring;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ErrorTest {

    private static final String ERROR_MESSAGE = "test error message.";

    @Test
    public void I_can_create_an_error_with_an_error_message() {

        assertEquals("the error message should be correct.", ERROR_MESSAGE, new Error(ERROR_MESSAGE).getError());
    }

    @Test
    public void I_can_create_an_error_with_an_exception_message() {

        assertEquals("the error message should be correct.", ERROR_MESSAGE,
                new Error(new Exception(ERROR_MESSAGE)).getError());
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_an_error_with_a_null_error_message() {

        new Error((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_an_error_with_a_null_exception() {

        new Error((Exception) null);
    }

    @Test
    public void I_can_check_the_equality_of_an_error_message() {

        final Error left = new Error(ERROR_MESSAGE);
        final Error right = new Error(new Exception(ERROR_MESSAGE));

        assertEquals("the same errors should be equal.", left, left);
        assertEquals("equivalent errors should be equal.", left, right);

        assertNotEquals("different errors not should be equal.", left, new Error("different"));
        assertNotEquals("an error should not be equal to an object.", left, new Object());
        assertNotEquals("an error should not be equal to null.", left, null);
    }

    @Test
    public void I_can_check_the_hash_code_of_an_error_message() {

        final Error left = new Error(ERROR_MESSAGE);
        final Error right = new Error(new Exception(ERROR_MESSAGE));

        assertEquals("equivalent errors hash codes should be equal.", left.hashCode(), right.hashCode());

        assertNotEquals("different errors hash code not should be equal.", left.hashCode(),
                new Error("different").hashCode());
        assertNotEquals("an error should not have the same hash cide as an object.", left.hashCode(),
                new Object().hashCode());
    }
}
