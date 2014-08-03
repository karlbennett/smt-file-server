package shiver.me.timbers.file.server;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import shiver.me.timbers.file.io.InvalidPathException;

import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * This class provides global controller behaviour e.g. error mapping.
 *
 * @author Karl Bennett
 */
@ControllerAdvice
@ResponseBody
public class GlobalControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public Map<String, String> invalidPath(InvalidPathException e) {

        return buildError(e);
    }

    private static Map<String, String> buildError(Throwable throwable) {

        return singletonMap("error", throwable.getMessage());
    }
}
