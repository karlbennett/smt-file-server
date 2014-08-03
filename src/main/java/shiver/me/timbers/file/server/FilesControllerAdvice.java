package shiver.me.timbers.file.server;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import shiver.me.timbers.file.io.InvalidPathException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * This class provides shared controller behaviour for the {@link DirectoryController} and {@link FileController}.
 *
 * @author Karl Bennett
 */
@ControllerAdvice(assignableTypes = {DirectoryController.class, FileController.class})
@ResponseBody
public class FilesControllerAdvice {

    @ModelAttribute
    public java.io.File absolutePath(HttpServletRequest request) {

        final Object path = request.getAttribute("absolutePath");

        if (null == path) {
            throw new InvalidPathException("No path provided.");
        }

        return new File(path.toString());
    }
}
