package shiver.me.timbers.file.server;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import shiver.me.timbers.file.io.InvalidPathException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static shiver.me.timbers.file.server.GlobalControllerAdvice.buildError;
import static shiver.me.timbers.file.server.Requests.getAbsoluteFile;

/**
 * This class provides shared controller behaviour for the {@link DirectoryController} and {@link FileController}.
 *
 * @author Karl Bennett
 */
@ControllerAdvice(assignableTypes = {DirectoryController.class, FileController.class})
@ResponseBody
public class FilesControllerAdvice {

    @ModelAttribute
    public File absoluteFile(HttpServletRequest request) {

        final File file = getAbsoluteFile(request);

        if (file.exists()) {

            return file;
        }

        throw new InvalidPathException();
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> noFileProvided(NoFileException e) {

        return buildError(e);
    }
}
