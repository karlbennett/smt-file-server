package shiver.me.timbers.file.server;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.Directory;
import shiver.me.timbers.file.io.InvalidPathException;
import shiver.me.timbers.file.io.JavaDirectory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller for mapping the file and directory requests.
 *
 * @author Karl Bennett
 */
@RestController
public class FilesController {

    @ModelAttribute
    public String path(HttpServletRequest request) {

        final Object path = request.getAttribute("path");

        if (null == path) {
            throw new InvalidPathException("No path provided.");
        }

        return path.toString();
    }

    @RequestMapping(value = "/directory", method = GET, produces = APPLICATION_JSON_VALUE)
    public Directory directory(@ModelAttribute String path) throws IOException {

        return new JavaDirectory(path);
    }

    @RequestMapping(value = "/file", method = GET)
    public ResponseEntity<FileSystemResource> file(@ModelAttribute String path) throws IOException {

        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(inspectMediaType(path));

        return new ResponseEntity<>(new FileSystemResource(new File(path)), headers, OK);
    }

    private static MediaType inspectMediaType(String path) throws IOException {

        final String mimeType = Files.probeContentType(Paths.get(path));

        return MediaType.valueOf(mimeType);
    }
}
