package shiver.me.timbers.file.server;

import org.apache.commons.io.FilenameUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static shiver.me.timbers.file.server.Requests.getAbsolutePathAttribute;

/**
 * This filter adds all the required response headers that inform a client that the server can hand partial HTTP
 * requests.
 *
 * @author Karl Bennett
 */
public class PartialRequestHeadersHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final DateTimeFormatter HTTP_DATE = DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss zzz");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        final String absolutePath = getAbsolutePathAttribute(request);

        final File file = new File(absolutePath);

        if (file.exists()) {

            response.setContentType(inspectMediaType(file).toString());
            response.setHeader("ETag", format("\"%s_%d_%d\"", file.getName(), file.length(), file.lastModified()));
            response.setHeader("Last-Modified", HTTP_DATE.print(file.lastModified()));
            response.setContentLength((int) file.length());
        }

        return true;
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
