package shiver.me.timbers.file.server;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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
    public void I_can_parse_no_ranges() {

        assertThat("some ranges should be produced.", new Ranges(), is(empty()));
    }

    @Test
    public void I_can_parse_some_ranges() {

        assertThat("some ranges should be produced.", ranges, hasSize(greaterThan(0)));
    }

    @Test
    public void I_can_parse_the_correct_number_of_ranges() {

        assertThat("the right number of ranges should be produced.", ranges, hasSize(3));
    }

    @Test
    public void I_can_parse_the_correct_ranges() {

        assertEquals("correct ranges should be produced.", RANGE_LIST, ranges);
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
    public void I_cannot_parse_a_range_header_value_with_only_ranges() {

        new Ranges(format("%s,%s,%s", RANGE_ONE, RANGE_TWO, RANGE_THREE), FILE_SIZE);
    }
}
