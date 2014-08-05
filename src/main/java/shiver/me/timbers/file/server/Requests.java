package shiver.me.timbers.file.server;

import shiver.me.timbers.file.io.InvalidPathException;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility methods for retrieving values from requests.
 *
 * @author Karl Bennett
 */
public class Requests {

    public static final String ABSOLUTE_PATH = "absolutePath";

    public static String getAbsolutePathAttribute(HttpServletRequest request) {

        final Object path = request.getAttribute(ABSOLUTE_PATH);

        if (null == path) {
            throw new InvalidPathException("No path provided.");
        }
        return path.toString();
    }
}
