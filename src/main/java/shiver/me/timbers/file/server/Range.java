package shiver.me.timbers.file.server;

import static java.lang.String.format;

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
    private final long size;
    private final boolean valid;

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

        this.size = deriveSize();

        this.valid = deriveValid();
    }

    public Range(long start, long end, long fileSize) {

        this.range = formatRange(start, end);
        this.fileSize = fileSize;
        this.start = start;
        this.end = end;
        this.size = deriveSize();
        this.valid = deriveValid();
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

    private long deriveSize() {
        return end - start + 1;
    }

    private boolean deriveValid() {
        return start <= end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getSize() {
        return size;
    }

    public boolean isValid() {
        return valid;
    }

    public long getFileSize() {
        return fileSize;
    }

    @Override
    public String toString() {
        return formatRange(start, end);
    }

    private static String formatRange(long start, long end) {
        return format("%d-%d", start, end);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Range)) return false;

        Range range = (Range) o;

        if (end != range.end) {
            return false;
        }
        if (fileSize != range.fileSize) {
            return false;
        }
        if (start != range.start) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = (int) (fileSize ^ (fileSize >>> 32));
        result = 31 * result + (int) (start ^ (start >>> 32));
        result = 31 * result + (int) (end ^ (end >>> 32));

        return result;
    }
}
