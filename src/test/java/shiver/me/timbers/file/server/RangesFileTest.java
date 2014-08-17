package shiver.me.timbers.file.server;

import org.junit.Test;
import org.mockito.Mockito;
import shiver.me.timbers.file.io.DefaultFileContentGetter;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.FileCreator;
import shiver.me.timbers.file.io.FileSystemElement;
import shiver.me.timbers.file.io.InvalidPathException;
import shiver.me.timbers.file.io.JavaFile;
import shiver.me.timbers.file.io.TestFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_have_correct_equality;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_produce_an_input_stream;
import static shiver.me.timbers.file.io.FileSteps.The_files_extension_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_input_stream_should_contain;
import static shiver.me.timbers.file.io.FileSteps.The_files_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_name_should_be_correct;
import static shiver.me.timbers.file.server.RangeFiles.buildRanges;
import static shiver.me.timbers.file.server.RangeFiles.buildRangesFile;

public class RangesFileTest {

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_ranges_file_with_a_null_file() {

        new RangesFile(null, mock(Ranges.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_create_a_ranges_file_with_empty_ranges() {

        final Ranges ranges = mock(Ranges.class);
        when(ranges.size()).thenReturn(0);

        new RangesFile(mock(File.class), ranges);
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_create_a_ranges_file_with_invalid_ranges() {

        final Ranges ranges = mock(Ranges.class);
        when(ranges.size()).thenReturn(1);
        when(ranges.isValid()).thenReturn(false);

        new RangesFile(mock(File.class), ranges);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_create_a_ranges_file_with_a_range_with_an_invalid_file_size() {

        final long fileSize = 3L;

        final File file = mock(File.class);
        when(file.getSize()).thenReturn(fileSize);

        new RangesFile(file, buildRanges(0, 1, 2));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_ranges_file_with_a_null_range() {

        new RangesFile(mock(File.class), null);
    }

    @Test
    public void I_can_get_a_files_name() {

        The_files_name_should_be_correct(new RangesFileCreator());
    }

    @Test
    public void I_can_get_a_files_extension() {

        The_files_extension_should_be_correct(new RangesFileCreator());
    }

    @Test
    public void I_can_get_a_files_modification_date() {

        The_files_modification_date_should_be_correct(new RangesFileCreator());
    }

    @Test
    public void I_can_check_the_equality_of_a_file() {

        The_file_should_have_correct_equality(new RangesFileCreator());

        assertNotEquals("the ranges file should not be equal to a range file with a different file.",
                buildRangesFile(FILE_ONE), buildRangesFile(FILE_TWO));

        assertNotEquals("the ranges file should not be equal to a range file with a different range.",
                buildRangesFile(FILE_ONE, 0, 1), buildRangesFile(FILE_ONE, 0, 2));
    }

    @Test
    public void I_can_to_string_a_file() {

        The_file_should_have_the_correct_to_string_value(new RangesFileCreator());
    }

    @Test
    public void I_can_serialise_a_file() {

        The_file_should_be_able_to_be_serialised(new RangesFileCreator());
    }

    @Test
    public void I_can_get_a_files_input_stream() {

        The_file_should_produce_an_input_stream(new RangesFileCreator());
    }

    @Test
    public void I_can_read_a_files_input_stream() {

        The_files_input_stream_should_contain(new RangesFileCreator());
    }

    @Test
    public void I_can_read_a_files_partial_input_stream() {

        final long start = 5;
        final long end = 10;

        The_files_input_stream_should_contain(
                new DefaultFileContentGetter<String>() {
                    @Override
                    public String get(TestFile<String> target) {

                        final String content = super.get(target);

                        return content.substring((int) start, (int) end + 1);
                    }
                },
                new RangesFileCreator(start, end)
        );
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_skip_an_invalid_input_stream() throws IOException {

        final InputStream input = mock(InputStream.class);
        when(input.skip(anyLong())).thenThrow(new IOException("test IO exception."));

        final long fileSize = 2L;

        final File file = mock(File.class);
        when(file.getInputStream()).thenReturn(input);
        when(file.getSize()).thenReturn(fileSize);

        new RangesFile(file, buildRanges(file, 0, 1)).getInputStream();
    }

    @Test
    public void I_can_iterate_over_the_contained_range_files() throws IOException {

        final Ranges ranges = buildRanges(FILE_ONE);

        int index = 0;

        for (RangeFile file : new RangesFile(FILE_ONE, ranges)) {

            assertEquals("the file should be correct.", new RangeFile(FILE_ONE, ranges.get(index++)), file);
        }
    }

    @Test
    public void I_can_get_all_the_contains_range_files() throws IOException {

        final Ranges ranges = buildRanges(FILE_ONE);

        final RangesFile file = new RangesFile(FILE_ONE, ranges);

        int index = 0;

        for (Range range : ranges) {

            assertEquals("the file should be correct.", new RangeFile(FILE_ONE, range), file.get(index++));
        }
    }

    @Test
    public void I_can_get_the_size_of_a_ranges_file() throws IOException {

        final Ranges ranges = buildRanges(FILE_ONE);

        assertThat("the ranges file should contain the correct number of range files.",
                new RangesFile(FILE_ONE, ranges), hasSize(ranges.size()));
    }

    private static class RangesFileCreator implements FileCreator {

        private final long start;
        private final long end;
        private final boolean useDefaults;

        private RangesFileCreator() {
            this.start = 0;
            this.end = 0;
            this.useDefaults = true;
        }

        private RangesFileCreator(long start, long end) {
            this.start = start;
            this.end = end;
            this.useDefaults = false;
        }

        @Override
        public File create(String path) {

            final File file = new JavaFile(path);

            final long start = useDefaults ? 0 : this.start;
            final long end = useDefaults ? file.getSize() : this.end;

            final long fileSize = file.getSize();

            return new RangesFile(file, buildRanges(start, end, fileSize));
        }

        @Override
        public FileSystemElement mock(String name, Date modified) {

            return mock(name, modified, "txt", 100);
        }

        @Override
        public File mock(String name, Date modified, String extension, long size) {

            final RangesFile mock = Mockito.mock(RangesFile.class);
            when(mock.getName()).thenReturn(name);
            when(mock.getModified()).thenReturn(modified);
            when(mock.getExtension()).thenReturn(extension);
            when(mock.getSize()).thenReturn(size);

            return mock;
        }
    }
}
