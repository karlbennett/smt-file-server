package shiver.me.timbers.file.server.spring;

/**
 * This exception is thrown when the {@link Requests#DIRECTORY} attribute isn't added to the
 * {@link javax.servlet.http.HttpServletRequest} that is sent into the {@link DirectoryController#directory} method.
 *
 * @author Karl Bennett
 */
public class NoDirectoryException extends RuntimeException {

    public NoDirectoryException() {
        super("No directory provided.");
    }
}