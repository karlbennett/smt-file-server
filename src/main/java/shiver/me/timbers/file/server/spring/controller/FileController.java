package shiver.me.timbers.file.server.spring.controller;

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
import shiver.me.timbers.file.server.spring.exception.Error;
import shiver.me.timbers.file.server.spring.exception.NoFileException;

import java.io.IOException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;
import static org.springframework.http.HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static shiver.me.timbers.file.server.spring.controller.Requests.RANGE;

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
    public shiver.me.timbers.file.server.spring.exception.Error noFileProvided(NoFileException e) {

        return new Error(e);
    }

    @ExceptionHandler
    @ResponseStatus(REQUESTED_RANGE_NOT_SATISFIABLE)
    public Error requestedRangeNotSatisfiable(RequestedRangeNotSatisfiableException e) {

        return new Error(e);
    }
}
