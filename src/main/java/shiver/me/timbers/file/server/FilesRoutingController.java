package shiver.me.timbers.file.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import shiver.me.timbers.file.io.InvalidPathException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller for routing the request to either a directory or file request.
 *
 * @author Karl Bennett
 */
@Controller
public class FilesRoutingController {

    @Autowired
    private String rootPath;

    @RequestMapping(method = GET)
    public String retrieve(HttpServletRequest request) throws IOException {

        final File file = new File(rootPath, request.getPathInfo()).getCanonicalFile();

        // Added the path as a request attribute so that it is available to the forwarding requests.
        // We add the path to a request attribute not a query parameter because this cannot be set in an incoming HTTP
        // request, that means arbitrary paths cannot be sent into the /directory and /file endpoints to access any path
        // in the file system.
        request.setAttribute("path", file.getPath());

        if (file.isDirectory()) {
            return "forward:/directory";
        }

        if (file.isFile()) {
            return "forward:/file";
        }

        throw new InvalidPathException("No such file or directory.");
    }
}
