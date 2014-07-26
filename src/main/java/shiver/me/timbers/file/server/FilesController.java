package shiver.me.timbers.file.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.Directory;
import shiver.me.timbers.file.io.InvalidPathException;
import shiver.me.timbers.file.io.JavaDirectory;
import shiver.me.timbers.file.io.JavaFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.concurrent.Callable;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller for mapping the file requests.
 *
 * @author Karl Bennett
 */
@RestController
public class FilesController {

    @Autowired
    private String rootPath;

    @RequestMapping(method = GET)
    public Callable<Object> retrieve(final HttpServletRequest request) {

        return new Callable<Object>() {

            @Override
            public Object call() throws Exception {

                final File file = new File(rootPath, request.getPathInfo()).getCanonicalFile();

                if (file.isDirectory()) {
                    return new JavaDirectory(file);
                }

                if (file.isFile()) {
                    return new FileSystemResource(file);
                }

                throw new InvalidPathException("No such file or directory.");
            }
        };
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public String invalidPath(InvalidPathException e) {

        return e.getMessage();
    }
}
