package shiver.me.timbers.file.server;

import org.junit.Test;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class RangeTest {

    private static final long FILE_SIZE = 10000L;

    @Test
    public void I_can_parse_a_range_value() {

        final Range range1 = new Range("0-499", FILE_SIZE);

        assertRangeIsCorrect(0, 499, range1);

        final Range range2 = new Range("500-999", FILE_SIZE);

        assertRangeIsCorrect(500, 999, range2);
    }

    @Test
    public void I_can_parse_a_range_value_with_only_an_end() {

        final Range range = new Range("-500", FILE_SIZE);

        assertRangeIsCorrect(9500, 9999, range);
    }

    @Test
    public void I_can_parse_a_range_value_with_only_an_end_that_is_larger_than_the_file_size() {

        final Range range = new Range("-10001", FILE_SIZE);

        assertRangeIsCorrect(0, 9999, range);
    }

    @Test
    public void I_can_parse_a_range_value_with_only_a_start() {

        final Range range = new Range("9500-", FILE_SIZE);

        assertRangeIsCorrect(9500, 9999, range);
    }

    @Test
    public void I_can_parse_a_range_value_with_an_end_equal_to_the_file_size() {

        final Range range = new Range(format("0-%d", FILE_SIZE), FILE_SIZE);

        assertRangeIsCorrect(0, 9999, range);
    }

    @Test
    public void I_can_parse_a_range_value_with_an_end_greater_than_the_file_size() {

        final Range range = new Range("0-10001", FILE_SIZE);

        assertRangeIsCorrect(0, 9999, range);
    }

    @Test
    public void I_can_parse_a_range_value_with_a_start_equal_to_the_end() {

        final Range range = new Range("500-500", FILE_SIZE);

        assertRangeIsCorrect(500, 500, range);
    }

    @Test
    public void I_can_parse_a_range_value_with_a_start_value_greater_than_the_end_value() {

        final Range range = new Range("500-0", FILE_SIZE);

        assertEquals("range start should be correct.", 500, range.getStart());
        assertEquals("range end should be correct.", 0, range.getEnd());
        assertFalse("range should be invalid.", range.isValid());
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_a_full_range_header_value() {

        new Range("bytes=0-500", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_range() {

        new Range("invalid", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_start_value() {

        new Range("invalid-999", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_end_value() {

        new Range("500-invalid", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_a_range_with_only_a_number() {

        new Range("500", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_a_range_with_a_start_value_greater_than_the_file_length() {

        new Range("10001-10002", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_a_range_with_only_a_start_value_greater_than_the_file_length() {

        new Range("10001-", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_a_null_range() {

        new Range(null, FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_empty_range() {

        new Range("", FILE_SIZE);
    }

    @Test
    public void I_can_to_string_a_range() {

        assertEquals("range toString should be correct", "{ \"start\" : 1, \"end\" : 2, \"fileSize\" : 3 }",
                new Range("1-2", 3).toString());
    }

    @Test
    public void I_can_to_check_the_equality_of_ranges() {

        final long start = 1;
        final long end = 3;
        final long fileSize = 5;

        final Range left = new Range(format("%d-%d", start, end), fileSize);
        final Range right = new Range(format("%d-%d", start, end), fileSize);

        assertEquals("the range should be equal to it's self.", left, left);

        assertEquals("the ranges should be equal.", left, right);

        assertEquals("the range hash codes should be equal.", left.hashCode(), right.hashCode());

        assertNotEquals("the range should not be equal to a range with a different start.", left,
                new Range(format("%d-%d", 2, end), fileSize));

        assertNotEquals("the range should not be equal to a range with a different end.", left,
                new Range(format("%d-%d", start, 4), fileSize));

        assertNotEquals("the range should not be equal to a range with a different fileSize.", left,
                new Range(format("%d-%d", start, end), 6));

        assertNotEquals("the range should not be equal to object.", left, new Object());

        assertNotEquals("the range should not be equal to null.", left, null);
    }

    private static void assertRangeIsCorrect(long start, long end, Range range) {

        assertEquals("range start should be correct.", start, range.getStart());
        assertEquals("range end should be correct.", end, range.getEnd());
        assertTrue("range should be valid.", range.isValid());
    }
}
