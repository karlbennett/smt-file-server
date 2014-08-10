package shiver.me.timbers.file.server;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import shiver.me.timbers.file.io.File;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * Utility methods for retrieving values from requests.
 *
 * @author Karl Bennett
 */
public class Requests {

    public static final String DIRECTORY = "directory";
    public static final String FILE = "file";

    private static final DateTimeFormatter HTTP_DATE = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss zzz");

    @SuppressWarnings("unchecked")
    public static <A> A getAttribute(String name, HttpServletRequest request, Creator<RuntimeException> creator) {

        final Object attribute = request.getAttribute(name);

        if (null == attribute) {
            throw creator.create();
        }
        return (A) attribute;
    }

    public static void addFileHeaders(HttpHeaders headers, File file) throws IOException {

        headers.setContentType(inspectMediaType(file));
        headers.setETag(format("\"%s_%d_%d\"", file.getName(), file.getSize(), file.getModified().getTime()));
        headers.set("Last-Modified", HTTP_DATE.print(file.getModified().getTime()));
        headers.setContentLength(file.getSize());
    }

    private static MediaType inspectMediaType(File file) throws IOException {

        final String mimeType = Files.probeContentType(Paths.get(file.toString()));

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
