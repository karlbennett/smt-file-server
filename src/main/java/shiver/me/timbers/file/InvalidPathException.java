package shiver.me.timbers.file;

/**
 * This exception is thrown when a file is {@link FileSystemElement} is created with a path to a file that does not
 * exist.
 *
 * @author Karl Bennett
 */
public class InvalidPathException extends IllegalArgumentException {

    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }
}
