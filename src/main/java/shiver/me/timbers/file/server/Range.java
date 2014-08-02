package shiver.me.timbers.file.server;

/**
 * This class parses an individual range value that has been previously extracted from an HTTP Range header.
 * e.g. "500-999" that was extracted from "Rage: bytes=0-499,500-999,1000-1499"
 *
 * @author Karl Bennett
 */
public class Range {

    private static final String HYPHEN = "-";

    private final String range;
    private final long fileSize;

    private final long start;
    private final long end;

    /**
     * @param range    must be in the format "\d*-\d*" where at least one of the numbers on either side is present.
     * @param fileSize the size of the file that the range is being requested for.
     */
    public Range(String range, long fileSize) {

        this.range = range;
        this.fileSize = fileSize;

        validRangeHeaderMustBeSupplied();

        this.start = deriveStart(range);

        startBoundaryMustBeLessThanTheFileSize();

        this.end = deriveEnd(range);
    }

    private void validRangeHeaderMustBeSupplied() {

        if (null == range || !range.contains(HYPHEN)) {
            throw new RequestedRangeNotSatisfiableException(range, fileSize);
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

    private void startBoundaryMustBeLessThanTheFileSize() {

        if (fileSize < start) {
            throw new RequestedRangeNotSatisfiableException(range, fileSize);
        }
    }

    private long deriveEnd(String range) {

        if (isPartialRange(range)) {
            return fileSize - 1;
        }

        final String[] boundaries = range.split("-");

        final long end = valueOf(boundaries[1]);

        if (isEquivalentToFileSize(end)) {
            return fileSize - 1;
        }

        return end;
    }

    private static boolean isPartialRange(String range) {
        return range.startsWith("-") || range.endsWith("-");
    }

    private boolean isEquivalentToFileSize(long end) {
        return fileSize <= end;
    }

    private long valueOf(String boundary) {

        try {

            return Long.valueOf(boundary);

        } catch (NumberFormatException e) {

            throw new RequestedRangeNotSatisfiableException(range, fileSize);
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
