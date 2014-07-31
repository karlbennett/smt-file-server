package shiver.me.timbers.file.server;

/**
 * The range of bytes that should be read from a file.
 *
 * @author Karl Bennett
 */
public class Range {

    private static final String BYTES = "bytes=";

    private final String rangeHeader;
    private final long fileSize;

    private final long start;
    private final long end;

    public Range(String rangeHeaderValue, long fileSize) {

        this.rangeHeader = rangeHeaderValue;
        this.fileSize = fileSize;

        rangeHeaderMustBeSupplied();

        final String rangeValue = rangeHeaderValue.replace(BYTES, "");
        final String[] ranges = rangeValue.split(",");

        final String firstRange = ranges[0];

        this.start = deriveStart(firstRange);

        startBoundaryMustBeLessThanTheFileSize();

        this.end = deriveEnd(firstRange);
    }

    private void rangeHeaderMustBeSupplied() {

        if (null == rangeHeader || !rangeHeader.startsWith(BYTES)) {
            throw new RequestedRangeNotSatisfiableException(rangeHeader, fileSize);
        }
    }

    private void startBoundaryMustBeLessThanTheFileSize() {

        if (fileSize < start) {
            throw new RequestedRangeNotSatisfiableException(rangeHeader, fileSize);
        }
    }

    private long deriveStart(String range) {

        if (range.startsWith("-")) {

            final long start = fileSize - valueOf(range.replace("-", ""));

            return 0 < start ? start : 0;
        }

        final String[] boundaries = range.split("-");

        return valueOf(boundaries[0]);
    }

    private long deriveEnd(String range) {

        if (isPartialRange(range)) {
            return fileSize - 1;
        }

        final String[] boundaries = splitRange(range);

        final long end = valueOf(boundaries[1]);

        if (isEquivalentToFileSize(end)) {
            return fileSize - 1;
        }

        return end;
    }

    private String[] splitRange(String range) {

        final String[] boundaries = range.split("-");

        thereMustBeTwoBoundaries(boundaries);

        return boundaries;
    }

    private static boolean isPartialRange(String range) {
        return range.startsWith("-") || range.endsWith("-");
    }

    private boolean isEquivalentToFileSize(long end) {
        return fileSize <= end;
    }

    private void thereMustBeTwoBoundaries(String[] boundaries) {

        if (2 != boundaries.length) {
            throw new RequestedRangeNotSatisfiableException(rangeHeader, fileSize);
        }
    }

    private long valueOf(String boundary) {

        try {

            return Long.valueOf(boundary);

        } catch (NumberFormatException e) {

            throw new RequestedRangeNotSatisfiableException(rangeHeader, fileSize);
        }
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public boolean isValid() {

        return start < end;
    }
}
