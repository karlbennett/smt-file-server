package shiver.me.timbers.file.server;

import org.junit.Test;
import shiver.me.timbers.file.io.InvalidPathException;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.file.server.GlobalControllerAdvice.buildError;

public class GlobalControllerAdviceTest {

    @Test
    public void I_can_build_an_invalid_path_message() {

        final InvalidPathException exception = new InvalidPathException();

        assertThat("the error message should be correct.", new GlobalControllerAdvice().invalidPath(exception),
                hasEntry("error", exception.getMessage()));
    }

    @Test
    public void I_can_build_an_error_message() {

        final Exception exception = new Exception("test message");

        assertThat("the error message should be correct.", buildError(exception),
                hasEntry("error", exception.getMessage()));
    }
}
