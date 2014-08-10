package shiver.me.timbers.file.server;

import org.junit.Test;
import org.mockito.Mockito;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.FileCreator;
import shiver.me.timbers.file.io.FileSystemElement;
import shiver.me.timbers.file.io.InvalidPathException;
import shiver.me.timbers.file.io.JavaFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertNotEquals;
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
import static shiver.me.timbers.file.server.RangeFiles.buildRangeFile;

public class RangeFileTest {

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_range_file_with_a_null_file() {

        new RangeFile(null, mock(Range.class));
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_create_a_range_file_with_a_range_with_an_invalid_file_size() {

        final File file = mock(File.class);
        when(file.getSize()).thenReturn(3L);

        new RangeFile(file, new Range(0, 1, 2));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_range_file_with_a_null_range() {

        new RangeFile(mock(File.class), null);
    }

    @Test
    public void I_can_get_a_files_name() {

        The_files_name_should_be_correct(new RangeFileCreator());
    }

    @Test
    public void I_can_get_a_files_extension() {

        The_files_extension_should_be_correct(new RangeFileCreator());
    }

    @Test
    public void I_can_get_a_files_modification_date() {

        The_files_modification_date_should_be_correct(new RangeFileCreator());
    }

    @Test
    public void I_can_check_the_equality_of_a_file() {

        The_file_should_have_correct_equality(new RangeFileCreator());

        assertNotEquals("the range file should not be equal to a range file with a different file.",
                buildRangeFile(FILE_ONE), buildRangeFile(FILE_TWO));

        assertNotEquals("the range file should not be equal to a range file with a different range.",
                buildRangeFile(FILE_ONE, 0, 1), buildRangeFile(FILE_ONE, 0, 2));
    }

    @Test
    public void I_can_to_string_a_file() {

        The_file_should_have_the_correct_to_string_value(new RangeFileCreator());
    }

    @Test
    public void I_can_serialise_a_file() {

        The_file_should_be_able_to_be_serialised(new RangeFileCreator());
    }

    @Test
    public void I_can_get_a_files_input_stream() {

        The_file_should_produce_an_input_stream(new RangeFileCreator());
    }

    @Test
    public void I_can_read_a_files_input_stream() {

        The_files_input_stream_should_contain(new RangeFileCreator());
    }

    @Test
    public void I_can_read_a_range_files_input_stream_that_contains_an_invalid_range() {

        The_files_input_stream_should_contain(new InvalidRangeFileCreator());
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_skip_an_invalid_input_stream() throws IOException {

        final InputStream input = mock(InputStream.class);
        when(input.skip(anyLong())).thenThrow(new IOException("test IO exception."));

        final File file = mock(File.class);
        when(file.getInputStream()).thenReturn(input);
        when(file.getSize()).thenReturn(2L);

        new RangeFile(file, new Range(0, 1, file.getSize())).getInputStream();
    }

    private static class RangeFileCreator implements FileCreator {

        @Override
        public File create(String path) {

            final File file = new JavaFile(path);

            return new RangeFile(file, new Range(0, file.getSize(), file.getSize()));
        }

        @Override
        public FileSystemElement mock(String name, Date modified) {

            return mock(name, modified, "txt", 100);
        }

        @Override
        public File mock(String name, Date modified, String extension, long size) {

            final RangeFile mock = Mockito.mock(RangeFile.class);
            when(mock.getName()).thenReturn(name);
            when(mock.getModified()).thenReturn(modified);
            when(mock.getExtension()).thenReturn(extension);
            when(mock.getSize()).thenReturn(size);

            return mock;
        }
    }

    private static class InvalidRangeFileCreator implements FileCreator {

        @Override
        public File create(String path) {

            final File file = new JavaFile(path);

            return new RangeFile(file, new Range(1, 0, file.getSize()));
        }

        @Override
        public FileSystemElement mock(String name, Date modified) {
            return mock(name, modified, null, 0);
        }

        @Override
        public File mock(String name, Date modified, String extension, long size) {
            throw new UnsupportedOperationException("no mock required for an invalid range file.");
        }
    }
}
