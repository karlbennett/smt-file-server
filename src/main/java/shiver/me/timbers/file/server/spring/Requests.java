package shiver.me.timbers.file.server.spring;

import shiver.me.timbers.file.io.Directory;
import shiver.me.timbers.file.io.StreamFile;
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

    public static Directory getDirectoryFrom(HttpServletRequest request) {
        // FIXME: Need unit tests.
        return getAttributeFrom(DIRECTORY, request, new Creator<RuntimeException>() {
            @Override
            public RuntimeException create() {
                return new NoDirectoryException();
            }
        });
    }

    public static StreamFile getFileFrom(HttpServletRequest request) {
        // FIXME: Need unit tests.
        return getAttributeFrom(FILE, request, new Creator<RuntimeException>() {
            @Override
            public RuntimeException create() {
                return new NoFileException();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <A> A getAttributeFrom(String name, HttpServletRequest request, Creator<RuntimeException> creator) {

        final Object attribute = request.getAttribute(name);

        if (null == attribute) {
            throw creator.create();
        }
        return (A) attribute;
    }
}
