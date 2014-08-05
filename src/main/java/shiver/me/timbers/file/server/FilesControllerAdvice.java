package shiver.me.timbers.file.server;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import shiver.me.timbers.file.io.InvalidPathException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

import static shiver.me.timbers.file.server.Requests.getAbsolutePathAttribute;

/**
 * This class provides shared controller behaviour for the {@link DirectoryController} and {@link FileController}.
 *
 * @author Karl Bennett
 */
@ControllerAdvice(assignableTypes = {DirectoryController.class, FileController.class})
@ResponseBody
public class FilesControllerAdvice {

    @ModelAttribute
    public File absolutePath(HttpServletRequest request) {

        final String path = getAbsolutePathAttribute(request);

        final File file = new File(path);

        if (file.exists()) {

            return file;
        }

        throw new InvalidPathException();
    }
}
