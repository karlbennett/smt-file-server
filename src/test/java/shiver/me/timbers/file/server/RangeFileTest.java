package shiver.me.timbers.file.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.InvalidPathException;
import shiver.me.timbers.file.io.StreamFile;
import shiver.me.timbers.file.io.StreamFileCreator;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.anyLong;
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
import static shiver.me.timbers.file.io.StreamFileSteps.The_file_should_produce_an_input_stream;
import static shiver.me.timbers.file.io.StreamFileSteps.The_files_input_stream_should_contain;
import static shiver.me.timbers.file.server.RangeFiles.buildRangeFile;

public class RangeFileTest {

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_range_file_with_a_null_file() {

        new RangeFile(null, mock(Range.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_create_a_range_file_with_an_invalid_range() {

        final Range range = mock(Range.class);
        when(range.isValid()).thenReturn(false);

        new RangeFile(mock(StreamFile.class), range);
    }

    @Test(expected = RequestedRangeNotSatisfiableException.class)
    public void I_cannot_create_a_range_file_with_a_range_with_an_invalid_file_size() {

        final StreamFile file = mock(StreamFile.class);
        when(file.getSize()).thenReturn(3L);

        new RangeFile(file, new Range(0, 1, 2));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_range_file_with_a_null_range() {

        new RangeFile(mock(StreamFile.class), null);
    }

    @Test
    public void I_can_get_a_files_name() {

        The_files_name_should_be_correct(new RangeFileCreator());
    }

    @Test
    public void I_can_get_a_files_modification_date() {

        The_files_modification_date_should_be_correct(new RangeFileCreator());
    }

    @Test
    public void I_can_get_a_files_extension() {

        The_files_extension_should_be_correct(new RangeFileCreator());
    }

    @Test
    public void I_can_get_a_files_size() {

        The_files_size_should_be_correct(new RangeFileCreator());
    }

    @Test
    public void I_can_get_a_files_mime_type() {

        The_files_mime_type_should_be_correct(new RangeFileCreator());
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
    public void I_can_serialise_a_file() throws JsonProcessingException {

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
    public void I_can_read_a_range_files_partial_input_stream() {

        final int start = 5;
        final int end = 10;

        The_files_input_stream_should_contain(new RangeFileContentGetter(start, end), new RangeFileCreator(start, end));
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_skip_a_range_files_invalid_input_stream() throws IOException {

        final InputStream input = mock(InputStream.class);
        when(input.skip(anyLong())).thenThrow(new IOException("test IO exception."));

        final StreamFile file = mock(StreamFile.class);
        when(file.getInputStream()).thenReturn(input);
        when(file.getSize()).thenReturn(2L);

        new RangeFileCreator().create(file, 0, 1).getInputStream();
    }

    public class RangeFileCreator extends AbstractPartialFileCreator<RangeFile>
            implements StreamFileCreator<RangeFile> {

        public RangeFileCreator() {
        }

        public RangeFileCreator(long start, long end) {
            super(start, end);
        }

        @Override
        public RangeFile create(StreamFile file, long start, long end) {

            return new RangeFile(file, new Range(start, end, file.getSize()));
        }

        @SuppressWarnings("unchecked")
        public <R extends File> Class<R> type() {

            return (Class<R>) RangeFile.class;
        }
    }
}
