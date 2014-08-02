package shiver.me.timbers.file.server;

import static java.lang.String.format;

/**
 * This exception is thrown when the request byte range of a file is malformed or out side the range of the files size.
 * That is the start of the range is greater than the length of the file. The end range can be greater than the file
 * size.
 *
 * @author Karl Bennett
 */
public class RequestedRangeNotSatisfiableException extends RuntimeException {

    public RequestedRangeNotSatisfiableException(String rangeHeader, long fileSize) {
        super(format("The supplied Range header is either malformed or out of bounds. Range: %s, File Size: %d",
                rangeHeader, fileSize));
    }
}
