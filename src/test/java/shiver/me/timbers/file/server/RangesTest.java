package shiver.me.timbers.file.server;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RangesTest {

    private static final long START_ONE = 0;
    private static final long END_ONE = 499;
    private static final long START_TWO = 500;
    private static final long END_TWO = 999;
    private static final long START_THREE = 1000;
    private static final long END_THREE = 1499;

    private static final String RANGE_ONE = format("%d-%d", START_ONE, END_ONE);
    private static final String RANGE_TWO = format("%d-%d", START_TWO, END_TWO);
    private static final String RANGE_THREE = format("%d-%d", START_THREE, END_THREE);

    private static final long FILE_SIZE = 1500;

    private static final List<Range> RANGE_LIST = new ArrayList<Range>() {{
        add(new Range(RANGE_ONE, FILE_SIZE));
        add(new Range(RANGE_TWO, FILE_SIZE));
        add(new Range(RANGE_THREE, FILE_SIZE));
    }};

    private static final String RANGE_HEADER_VALUE = format("bytes=%s,%s,%s", RANGE_ONE, RANGE_TWO, RANGE_THREE);

    private Ranges ranges;

    @Before
    public void setUp() {

        ranges = new Ranges(RANGE_HEADER_VALUE, FILE_SIZE);
    }

    @Test
    public void I_can_create_ranges_with_an_array_of_ranges() {

        assertEquals("the ranges should contain all the ranges.", RANGE_LIST,
                new Ranges(FILE_SIZE, RANGE_LIST.get(0), RANGE_LIST.get(1), RANGE_LIST.get(2)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_create_ranges_with_a_range_with_an_invalid_file_size() {

        new Ranges(FILE_SIZE, RANGE_LIST.get(0), new Range(0, 1, 2), RANGE_LIST.get(2));
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_create_ranges_with_a_range_with_an_invalid_start() {

        final Range range = mock(Range.class);
        when(range.getFileSize()).thenReturn(FILE_SIZE);
        when(range.getStart()).thenReturn(FILE_SIZE + 1);

        new Ranges(FILE_SIZE, RANGE_LIST.get(0), range, RANGE_LIST.get(2));
    }

    @Test
    public void I_can_parse_some_ranges() {

        assertThat("some ranges should be produced.", ranges, hasSize(greaterThan(0)));
        assertTrue("ranges should be valid.", ranges.isValid());
    }

    @Test
    public void I_can_parse_the_correct_number_of_ranges() {

        assertThat("the right number of ranges should be produced.", ranges, hasSize(3));
        assertTrue("ranges should be valid.", ranges.isValid());
    }

    @Test
    public void I_can_parse_the_correct_ranges() {

        assertEquals("correct ranges should be produced.", RANGE_LIST, ranges);
        assertTrue("ranges should be valid.", ranges.isValid());
    }

    @Test
    public void I_can_parse_a_ranges_that_contains_a_range_with_a_start_value_greater_than_the_end_value() {

        final Range wrongRange = new Range("999-500", FILE_SIZE);

        final Ranges ranges = new Ranges(format("bytes=%s,%s,%s", RANGE_ONE, wrongRange, RANGE_THREE), FILE_SIZE);

        assertEquals("correct ranges should be produced.", asList(RANGE_LIST.get(0), wrongRange, RANGE_LIST.get(2)),
                ranges);
        assertFalse("ranges should be invalid.", ranges.isValid());
    }

    @Test
    public void I_can_parse_a_ranges_that_contains_only_ranges_with_a_start_value_greater_than_the_end_value() {

        final Range wrongRangeOne = new Range("499-0", FILE_SIZE);
        final Range wrongRangeTwO = new Range("999-500", FILE_SIZE);
        final Range wrongRangeThree = new Range("1499-1000", FILE_SIZE);

        final Ranges ranges = new Ranges(format("bytes=%s,%s,%s", wrongRangeOne, wrongRangeTwO, wrongRangeThree),
                FILE_SIZE);

        assertEquals("correct ranges should be produced.", asList(wrongRangeOne, wrongRangeTwO, wrongRangeThree),
                ranges);
        assertFalse("ranges should be invalid.", ranges.isValid());
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_range_header_value() {

        new Ranges("invalid", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_range_header_value_with_invalid_ranges() {

        new Ranges("bytes=invalid", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_range_header_value_with_invalid_start_range() {

        new Ranges("bytes=invalid-499", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_range_header_value_with_invalid_end_range() {

        new Ranges("bytes=0-invalid", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_range_header_value_with_invalid_prefix_range() {

        new Ranges("bytes=invalid-", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_an_invalid_range_header_value_with_invalid_suffix_range() {

        new Ranges("bytes=-invalid", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_no_ranges() {

        new Ranges("bytes=", FILE_SIZE);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_parse_a_range_header_value_with_only_ranges() {

        new Ranges(format("%s,%s,%s", RANGE_ONE, RANGE_TWO, RANGE_THREE), FILE_SIZE);
    }

    @Test
    public void I_can_to_string_a_ranges() {

        assertEquals("the ranges toString should be correct.", RANGE_HEADER_VALUE, ranges.toString());
    }

    @Test
    public void I_can_to_check_the_equality_of_ranges() {

        final Ranges left = new Ranges(RANGE_HEADER_VALUE, FILE_SIZE);
        final Ranges right = new Ranges(RANGE_HEADER_VALUE, FILE_SIZE);

        assertEquals("the ranges should be equal to it's self.", left, left);

        assertEquals("the ranges should be equal.", left, right);

        assertEquals("the ranges hash codes should be equal.", left.hashCode(), right.hashCode());

        assertNotEquals("the ranges should not be equal to a ranges with different ranges.", left,
                new Ranges(format("bytes=%s,%s", RANGE_ONE, RANGE_THREE), FILE_SIZE));

        assertNotEquals("the range should not be equal to a ranges with a different fileSize.", left,
                new Ranges(RANGE_HEADER_VALUE, 2000));

        assertNotEquals("the ranges should not be equal to object.", left, new Object());

        assertNotEquals("the ranges should not be equal to null.", left, null);
    }
}
