package shiver.me.timbers.file.server.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiver.me.timbers.file.io.InvalidPathException;
import shiver.me.timbers.file.io.JavaDirectory;
import shiver.me.timbers.file.io.JavaStreamFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static shiver.me.timbers.file.server.spring.Requests.DIRECTORY;
import static shiver.me.timbers.file.server.spring.Requests.FILE;

/**
 * Controller for routing the request to either a directory or file request.
 *
 * @author Karl Bennett
 */
@Controller
public class FilesRoutingController {

    @Autowired
    private String rootPath;

    @RequestMapping(method = {GET, HEAD})
    public String retrieve(HttpServletRequest request) throws IOException {

        final File file = new File(rootPath, request.getPathInfo()).getCanonicalFile();

        if (pathOutsideOfRootDirectory(file)) {

            throw new InvalidPathException();
        }

        if (file.isDirectory()) {

            request.setAttribute(DIRECTORY, new JavaDirectory(file));

            return "forward:/directory";
        }

        if (file.isFile()) {

            request.setAttribute(FILE, new JavaStreamFile(file));

            return "forward:/file";
        }

        throw new InvalidPathException();
    }

    private boolean pathOutsideOfRootDirectory(File file) {
        return !file.getPath().contains(rootPath);
    }
}
