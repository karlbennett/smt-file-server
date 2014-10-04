package shiver.me.timbers.file.server.spring.controller;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import shiver.me.timbers.file.io.File;

import java.io.IOException;

import static java.lang.String.format;

/**
 * Utility methods for adding values to responses.
 *
 * @author Karl Bennett
 */
public class Responses {

    public static final String CONTENT_RANGE = "Content-Range";

    private static final DateTimeFormatter HTTP_DATE = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss zzz");

    public static void addFileHeaders(HttpHeaders headers, File file) throws IOException {

        headers.set("Accept-Ranges", "bytes");
        headers.setContentType(MediaType.valueOf(file.getMimeType()));
        headers.setETag(format("\"%s_%d_%d\"", file.getName(), file.getSize(), file.getModified().getTime()));
        headers.set("Last-Modified", HTTP_DATE.print(file.getModified().getTime()));
        headers.setContentLength(file.getSize());
    }
}
