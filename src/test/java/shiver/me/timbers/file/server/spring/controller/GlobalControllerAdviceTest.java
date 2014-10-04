package shiver.me.timbers.file.server.spring.controller;

import org.junit.Test;
import shiver.me.timbers.file.io.InvalidPathException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class GlobalControllerAdviceTest {

    @Test
    public void I_can_build_an_invalid_path_message() {

        final InvalidPathException exception = new InvalidPathException();

        final shiver.me.timbers.file.server.spring.exception.Error entity = new GlobalControllerAdvice().invalidPath(exception);

        assertThat("the error message should be correct.", entity.getError(), equalTo(exception.getMessage()));
    }
}
