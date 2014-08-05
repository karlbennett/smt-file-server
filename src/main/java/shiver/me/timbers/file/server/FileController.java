package shiver.me.timbers.file.server;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shiver.me.timbers.file.io.InvalidPathException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;
import static org.springframework.http.HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.HEAD;
import static shiver.me.timbers.file.server.GlobalControllerAdvice.buildError;
import static shiver.me.timbers.file.server.Requests.getAbsolutePathAttribute;

/**
 * Controller for mapping the file and directory requests.
 *
 * @author Karl Bennett
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private static final int DEFAULT_BUFFER_SIZE = 10240;

    private static final String RANGE = "Range";
    private static final String CONTENT_RANGE = "Content-Range";

    @InitBinder
    public void initBinder(final HttpServletRequest request, WebDataBinder binder) throws IOException {

        binder.registerCustomEditor(Ranges.class, new PropertyEditorSupport() {

            @Override
            public void setAsText(String text) throws IllegalArgumentException {

                final String absolutePath = getAbsolutePathAttribute(request);

                final long fileSize = fileSize(absolutePath);

                setValue(new Ranges(text, fileSize));
            }
        });
    }

    private static long fileSize(String absolutePath) {

        try {

            return Files.size(Paths.get(absolutePath));

        } catch (IOException e) {

            throw new InvalidPathException(e);
        }
    }

    @RequestMapping(method = HEAD)
    public void fileHead()
            throws IOException {
    }

    @RequestMapping(method = HEAD, headers = RANGE)
    @ResponseStatus(PARTIAL_CONTENT)
    public HttpHeaders fileHead(@RequestHeader(value = RANGE) Ranges ranges, File file)
            throws IOException {

        final HttpHeaders headers = new HttpHeaders();

        if (!ranges.isValid()) {

            return headers;
        }

        addContentRange(ranges, file, headers);

        return headers;
    }

    @RequestMapping(method = GET)
    public FileSystemResource file(File file) throws IOException {

        return new FileSystemResource(file);
    }

    @RequestMapping(method = GET, headers = RANGE)
    public void file(@RequestHeader(value = RANGE) Ranges ranges, File file, HttpServletResponse response)
            throws IOException {

        response.setStatus(PARTIAL_CONTENT.value());

        if (!ranges.isValid()) {

            copyToResponse(file, response);

            return;
        }

        addContentRange(ranges, file, response);

        final Range range = ranges.get(0);

        copy(range.getStart(), range.getEnd(), file, response);
    }

    private static void addContentRange(Ranges ranges, File file, HttpHeaders headers) {

        headers.set(CONTENT_RANGE, format("bytes %s/%d", ranges.get(0), file.length()));
    }

    private static void addContentRange(Ranges ranges, File file, HttpServletResponse response) {

        response.setHeader(CONTENT_RANGE, format("bytes %s/%d", ranges.get(0), file.length()));
    }

    private void copy(long start, long end, File file, HttpServletResponse response) throws IOException {

        final long length = end - start + 1;

        if (file.length() <= length) {

            copyToResponse(file, response);

            return;
        }

        try (
                final RandomAccessFile input = new RandomAccessFile(file, "r");
                final OutputStream output = response.getOutputStream()
        ) {

            input.seek(start);

            final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int read;

            long toRead = length;

            while ((read = input.read(buffer)) > 0 && (toRead -= read) > 0) {
                output.write(buffer, 0, read);
            }

            output.write(buffer, 0, (int) toRead + read);
        }
    }

    private static void copyToResponse(File file, HttpServletResponse response) throws IOException {
        try (OutputStream out = response.getOutputStream()) {

            IOUtils.copy(new FileInputStream(file), out);
        }
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> requestedRangeNotSatisfiable(RequestedRangeNotSatisfiableException e) {

        final HttpHeaders headers = new HttpHeaders();

        headers.set(CONTENT_RANGE, format("bytes */%d", e.getFileSize()));

        return new ResponseEntity<>(buildError(e), headers, REQUESTED_RANGE_NOT_SATISFIABLE);
    }
}
