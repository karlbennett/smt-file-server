package shiver.me.timbers.file.server;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.Directory;
import shiver.me.timbers.file.io.InvalidPathException;
import shiver.me.timbers.file.io.JavaDirectory;
import shiver.me.timbers.file.io.JavaFile;

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
@RequestMapping("/")
public class FilesController {

    @RequestMapping(value = "/{file}", method = GET, produces = APPLICATION_JSON_VALUE)
    public Callable<Object> retrieve(@PathVariable final File file) {

        return new Callable<Object>() {

            @Override
            public Object call() throws Exception {

                if (file.isDirectory()) {
                    return new JavaDirectory(file);
                }

                if (file.isFile()) {
                    return new JavaFile(file).getInputStream();
                }

                throw new InvalidPathException("No such file or directory.");
            }
        };
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Callable<Directory> retrieve() {

        return new Callable<Directory>() {

            @Override
            public Directory call() throws Exception {

                return new JavaDirectory("");
            }
        };
    }

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public String invalidPath(InvalidPathException e) {

        return e.getMessage();
    }
}
