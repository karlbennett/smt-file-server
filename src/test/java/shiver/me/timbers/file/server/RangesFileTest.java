package shiver.me.timbers.file.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.StreamFile;

import java.io.IOException;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_have_correct_equality;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileSteps.The_files_extension_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_mime_type_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_name_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_size_should_be_correct;
import static shiver.me.timbers.file.server.RangeFiles.buildRanges;
import static shiver.me.timbers.file.server.RangeFiles.buildRangesFile;
import static shiver.me.timbers.file.server.RangeFiles.buildRangesWithMocks;
import static shiver.me.timbers.file.server.RangeFiles.mockRange;

public class RangesFileTest {

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_ranges_file_with_a_null_file() {

        new RangesFile(null, mock(Ranges.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_create_a_ranges_file_with_empty_ranges() {

        final Ranges ranges = mock(Ranges.class);
        when(ranges.size()).thenReturn(0);

        new RangesFile(mock(StreamFile.class), ranges);
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_create_a_ranges_file_with_invalid_ranges() {

        final Ranges ranges = mock(Ranges.class);
        when(ranges.size()).thenReturn(1);
        when(ranges.isValid()).thenReturn(false);

        new RangesFile(mock(StreamFile.class), ranges);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_create_a_ranges_file_with_a_range_with_an_invalid_file_size() {

        final long fileSize = 3L;

        final StreamFile file = mock(StreamFile.class);
        when(file.getSize()).thenReturn(fileSize);

        new RangesFile(file, buildRanges(0, 1, 2));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_ranges_file_with_a_null_range() {

        new RangesFile(mock(StreamFile.class), null);
    }

    @Test
    public void I_can_get_a_files_name() {

        The_files_name_should_be_correct(new RangesFileCreator());
    }

    @Test
    public void I_can_get_a_files_modification_date() {

        The_files_modification_date_should_be_correct(new RangesFileCreator());
    }

    @Test
    public void I_can_get_a_files_extension() {

        The_files_extension_should_be_correct(new RangesFileCreator());
    }

    @Test
    public void I_can_get_a_files_size() {

        The_files_size_should_be_correct(new RangesFileCreator());
    }

    @Test
    public void I_can_get_a_files_mime_type() {

        The_files_mime_type_should_be_correct(new RangesFileCreator());
    }

    @Test
    public void I_can_check_the_equality_of_a_file() {

        The_file_should_have_correct_equality(new RangesFileCreator());

        assertNotEquals("the ranges file should not be equal to a range file with a different file.",
                buildRangesFile(FILE_ONE), buildRangesFile(FILE_TWO));

        assertNotEquals("the ranges file should not be equal to a range file with a different range.",
                buildRangesFile(FILE_ONE, 0, 1), buildRangesFile(FILE_ONE, 0, 2));

        assertNotEquals("the ranges file should not be equal to a range file with a different ranges.",
                buildRangesFile(FILE_ONE, buildRanges(FILE_ONE, mockRange(FILE_ONE, 0, 1), mockRange(FILE_ONE, 2, 3))),
                buildRangesFile(FILE_ONE, buildRanges(FILE_ONE, mockRange(FILE_ONE, 4, 5), mockRange(FILE_ONE, 6, 7))));
    }

    @Test
    public void I_can_to_string_a_file() {

        The_file_should_have_the_correct_to_string_value(new RangesFileCreator());
    }

    @Test
    public void I_can_serialise_a_file() throws JsonProcessingException {

        The_file_should_be_able_to_be_serialised(new RangesFileCreator());
    }

    @Test
    public void I_can_iterate_over_the_contained_range_files() throws IOException {

        final Ranges ranges = buildRangesWithMocks(FILE_ONE);

        int index = 0;

        for (RangeFile file : new RangesFile(FILE_ONE, ranges)) {

            assertEquals("the file should be correct.", new RangeFile(FILE_ONE, ranges.get(index++)), file);
        }
    }

    @Test
    public void I_can_get_all_the_contained_ranges() throws IOException {

        final Ranges ranges = buildRangesWithMocks(FILE_ONE);

        assertEquals("the ranges file ranges should be correct.", ranges, new RangesFile(FILE_ONE, ranges).getRanges());
    }

    @Test
    public void I_can_get_all_the_contained_range_files() throws IOException {

        final Ranges ranges = buildRangesWithMocks(FILE_ONE);

        final RangesFile file = new RangesFile(FILE_ONE, ranges);

        int index = 0;

        for (Range range : ranges) {

            assertEquals("the file should be correct.", new RangeFile(FILE_ONE, range), file.get(index++));
        }
    }

    @Test
    public void I_can_get_the_size_of_a_ranges_file() throws IOException {

        final Ranges ranges = buildRangesWithMocks(FILE_ONE);

        assertThat("the ranges file should contain the correct number of range files.",
                new RangesFile(FILE_ONE, ranges), hasSize(ranges.size()));
    }

    private static class RangesFileCreator extends AbstractPartialFileCreator<RangesFile> {

        @Override
        public RangesFile create(StreamFile file, long start, long end) {

            return new RangesFile(file, buildRanges(start, end, file.getSize()));
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R extends File> Class<R> type() {
            return (Class<R>) RangesFile.class;
        }
    }
}
