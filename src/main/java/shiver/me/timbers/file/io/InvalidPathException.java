package shiver.me.timbers.file.io;

/**
 * This exception is thrown when a file is {@link FileSystemElement} is created with a path to a file that does not
 * exist.
 *
 * @author Karl Bennett
 */
public class InvalidPathException extends IllegalArgumentException {

    private static final String DEFAULT_MESSAGE = "No such file or directory.";

    public InvalidPathException() {
        this(DEFAULT_MESSAGE);
    }

    public InvalidPathException(Throwable cause) {
        this(DEFAULT_MESSAGE, cause);
    }

    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }
}
