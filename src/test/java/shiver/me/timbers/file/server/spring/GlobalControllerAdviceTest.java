package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import shiver.me.timbers.file.io.InvalidPathException;

import java.util.Map;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.file.server.spring.GlobalControllerAdvice.buildError;

public class GlobalControllerAdviceTest {

    @Test
    public void I_can_build_an_invalid_path_message() {

        final InvalidPathException exception = new InvalidPathException();

        final Map<String, String> entity = new GlobalControllerAdvice().invalidPath(exception);

        assertResponseEntity(exception, entity);
    }

    @Test
    public void I_can_build_an_error_message() {

        final Exception exception = new Exception("test message");

        final Map<String, String> entity = buildError(exception);

        assertResponseEntity(exception, entity);
    }

    private static void assertResponseEntity(Exception exception, Map<String, String> entity) {

        assertThat("the error message should be correct.", entity, hasEntry("error", exception.getMessage()));
    }
}
