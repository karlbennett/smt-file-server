package shiver.me.timbers.file.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.Directory;
import shiver.me.timbers.file.io.JavaDirectory;

import java.io.File;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller for mapping the file and directory requests.
 *
 * @author Karl Bennett
 */
@RestController
@RequestMapping("/directory")
public class DirectoryController {

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Directory directory(File directory) throws IOException {

        return new JavaDirectory(directory);
    }
}
