package shiver.me.timbers.file.server.spring;

import shiver.me.timbers.file.server.Creator;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility methods for retrieving values from requests.
 *
 * @author Karl Bennett
 */
public class Requests {

    public static final String DIRECTORY = "directory";
    public static final String FILE = "file";

    public static final String RANGE = "Range";

    @SuppressWarnings("unchecked")
    public static <A> A getAttribute(String name, HttpServletRequest request, Creator<RuntimeException> creator) {

        final Object attribute = request.getAttribute(name);

        if (null == attribute) {
            throw creator.create();
        }
        return (A) attribute;
    }
}
