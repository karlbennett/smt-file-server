package shiver.me.timbers.file.server;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
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

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;

/**
 * Controller for mapping the file and directory requests.
 *
 * @author Karl Bennett
 */
@RestController
public class FilesController {

    private static final DateTimeFormatter HTTP_DATE = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss zzz");

    @ModelAttribute
    public File absolutePath(HttpServletRequest request) {

        final Object path = request.getAttribute("absolutePath");

        if (null == path) {
            throw new InvalidPathException("No path provided.");
        }

        return new File(path.toString());
    }

    @RequestMapping(value = "/directory", method = GET, produces = APPLICATION_JSON_VALUE)
    public Directory directory(File directory) throws IOException {

        return new JavaDirectory(directory);
    }

    @RequestMapping(value = "/file", method = HEAD)
    public HttpHeaders fileHead(File file) throws IOException {

        return buildFileHeaders(file);
    }

    @RequestMapping(value = "/file", method = GET)
    public ResponseEntity<FileSystemResource> file(File file) throws IOException {

        final HttpHeaders headers = buildFileHeaders(file);

        return new ResponseEntity<>(new FileSystemResource(file), headers, OK);
    }

    private static HttpHeaders buildFileHeaders(File file) throws IOException {

        final HttpHeaders headers = new HttpHeaders();

        headers.setContentType(inspectMediaType(file));
        headers.set("Accept-Ranges", "bytes");
        headers.setETag(format("\"%s_%d\"", file.getName(), file.lastModified()));
        headers.set("Last-Modified", HTTP_DATE.print(file.lastModified()));
        headers.setContentLength(file.length());

        return headers;
    }

    private static MediaType inspectMediaType(File file) throws IOException {

        final String mimeType = Files.probeContentType(Paths.get(file.getPath()));

        // It seem that at the moment Files.probeContentType(Paths) returns a mime type of "text/plain" for JSON files.
        if (isJsonFile(file, mimeType)) {

            return APPLICATION_JSON;
        }

        return MediaType.valueOf(mimeType);
    }

    private static boolean isJsonFile(File file, String mimeType) {

        return TEXT_PLAIN_VALUE.equals(mimeType) && "json".equalsIgnoreCase(FilenameUtils.getExtension(file.getName()));
    }
}
