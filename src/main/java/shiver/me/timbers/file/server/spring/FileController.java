package shiver.me.timbers.file.server.spring;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.StreamFile;
import shiver.me.timbers.file.server.RangeFile;
import shiver.me.timbers.file.server.Ranges;
import shiver.me.timbers.file.server.RangesFile;
import shiver.me.timbers.file.server.RequestedRangeNotSatisfiableException;

import java.io.IOException;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;
import static org.springframework.http.HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static shiver.me.timbers.file.server.spring.GlobalControllerAdvice.buildError;
import static shiver.me.timbers.file.server.spring.Requests.RANGE;
import static shiver.me.timbers.file.server.spring.Responses.CONTENT_RANGE;

/**
 * Controller for mapping the file and directory requests.
 *
 * @author Karl Bennett
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping(method = {GET, HEAD})
    public StreamFile file(StreamFile file) throws IOException {

        return file;
    }

    @RequestMapping(method = {GET, HEAD}, headers = RANGE)
    @ResponseStatus(PARTIAL_CONTENT)
    public File file(Ranges ranges, StreamFile file) throws IOException {

        // We must ignore any invalid range headers.
        if (!ranges.isValid()) {
            return file;
        }

        if (1 == ranges.size()) {

            return new RangeFile(file, ranges.get(0));
        }

        return new RangesFile(file, ranges);
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> noFileProvided(NoFileException e) {

        return buildError(e);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> requestedRangeNotSatisfiable(RequestedRangeNotSatisfiableException e) {

        final HttpHeaders headers = new HttpHeaders();

        headers.set(CONTENT_RANGE, format("bytes */%d", e.getFileSize()));

        return new ResponseEntity<>(buildError(e), headers, REQUESTED_RANGE_NOT_SATISFIABLE);
    }
}
