package shiver.me.timbers.file.server;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.Directory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static shiver.me.timbers.file.server.GlobalControllerAdvice.buildError;
import static shiver.me.timbers.file.server.Requests.DIRECTORY;
import static shiver.me.timbers.file.server.Requests.getAttribute;

/**
 * Controller for mapping the file and directory requests.
 *
 * @author Karl Bennett
 */
@RestController
@RequestMapping("/directory")
public class DirectoryController {

    @ModelAttribute
    public Directory directory(HttpServletRequest request) {

        return getAttribute(DIRECTORY, request, new Creator<RuntimeException>() {
            @Override
            public RuntimeException create() {
                return new NoDirectoryException();
            }
        });
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Directory directory(Directory directory) throws IOException {

        return directory;
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> noDirectoryProvided(NoDirectoryException e) {

        return buildError(e);
    }

    /**
     * This exception is thrown when the {@link Requests#DIRECTORY} attribute isn't added to the
     * {@link javax.servlet.http.HttpServletRequest} that is sent into the {@link #directory} method.
     *
     * @author Karl Bennett
     */
    private static class NoDirectoryException extends RuntimeException {

        public NoDirectoryException() {
            super("No directory provided.");
        }
    }
}
