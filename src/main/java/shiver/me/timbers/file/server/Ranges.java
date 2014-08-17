package shiver.me.timbers.file.server;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses a Range header ({@code Range: bytes=0-499,500-999,1000-1499}) value into a list of {@link Range}s.
 *
 * @author Karl Bennett
 */
public class Ranges extends AbstractList<Range> {

    private static final String BYTES = "bytes=";

    private final List<Range> ranges;
    private boolean valid = true;

    public Ranges(long fileSize, Range... ranges) {

        this.ranges = new ArrayList<>(ranges.length);

        for (Range range : ranges) {

            startBoundaryMustBeLessThanTheFileSize(range, fileSize);

            setOverallValidityWith(range);

            this.ranges.add(range);
        }
    }

    /**
     * @param rangeHeaderValue must be in the format "bytes=(\d*-\d*)*" where at least one of the numbers on either side
     *                         in each range is present.
     * @param fileSize         the size of the file that the range is being requested for.
     */
    public Ranges(String rangeHeaderValue, long fileSize) {

        rangeHeaderValueShouldBeValid(rangeHeaderValue, fileSize);

        final String rangeValue = rangeHeaderValue.replace(BYTES, "");
        final String[] rangeStrings = rangeValue.split(",");

        this.ranges = new ArrayList<>(rangeStrings.length);

        rangesMustBeSupplied(rangeStrings, rangeHeaderValue, fileSize);

        for (String rangeString : rangeStrings) {

            final Range range = new Range(rangeString, fileSize);

            setOverallValidityWith(range);

            this.ranges.add(range);
        }
    }

    private static void startBoundaryMustBeLessThanTheFileSize(Range range, long fileSize) {

        if (fileSize < range.getStart()) {
            throw new RequestedRangeNotSatisfiableException(range.toString(), fileSize);
        }
    }

    private static void rangeHeaderValueShouldBeValid(String rangeHeaderValue, long fileSize) {

        if (!rangeHeaderValue.contains(BYTES)) {
            throw new RequestedRangeNotSatisfiableException(rangeHeaderValue, fileSize);
        }
    }

    private static void rangesMustBeSupplied(String[] rangeStrings, String rangeHeaderValue, long fileSize) {

        if (1 == rangeStrings.length && "".equals(rangeStrings[0])) {
            throw new RequestedRangeNotSatisfiableException(rangeHeaderValue, fileSize);
        }
    }

    private void setOverallValidityWith(Range range) {

        if (!range.isValid()) {
            valid = false;
        }
    }

    @Override
    public Range get(int index) {
        return ranges.get(index);
    }

    @Override
    public int size() {
        return ranges.size();
    }

    public boolean isValid() {

        return valid;
    }

    @Override
    public String toString() {

        final StringBuilder toString = new StringBuilder(BYTES);

        for (Range range : ranges) {

            toString.append(range).append(',');
        }

        removeLastComma(toString);

        return toString.toString();
    }

    private void removeLastComma(StringBuilder toString) {

        final int length = toString.length();

        toString.replace(length - 1, length, "");
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (!(object instanceof Ranges)) {
            return false;
        }
        if (!super.equals(object)) { // This equality check will check the ranges field as well.
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + ranges.hashCode();
        return result;
    }
}
