package shiver.me.timbers.file.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.DirectoryConstants.CURRENT_DIRECTORY;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_have_correct_equality;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileSteps.The_files_extension_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_mime_type_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_name_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_size_should_be_correct;
import static shiver.me.timbers.file.io.JavaStreamFile.getInputStream;
import static shiver.me.timbers.file.io.StreamFileSteps.The_file_should_produce_an_input_stream;
import static shiver.me.timbers.file.io.StreamFileSteps.The_files_input_stream_should_contain;

public class JavaStreamFileTest {

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_java_stream_file_with_a_null_path() {

        new JavaStreamFile((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_java_stream_file_with_a_null_file() {

        new JavaStreamFile((java.io.File) null);
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_stream_file_with_a_path_to_a_directory() {

        new JavaStreamFile(DIRECTORY_ONE.getAbsolutePath());
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_stream_file_with_a_path_to_non_existent_file() {

        new JavaStreamFile("this/file/does/not/exist");
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_stream_file_with_an_invalid_path() throws IOException {

        final java.io.File file = mock(java.io.File.class);
        when(file.getCanonicalFile()).thenThrow(new IOException());

        new JavaStreamFile(file);
    }

    @Test
    public void I_can_create_a_java_stream_file_with_a_root_and_path() {

        new JavaStreamFile(CURRENT_DIRECTORY.getAbsolutePath(), FILE_ONE.getPath());
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_stream_file_with_a_dot() {

        new JavaStreamFile(".");
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_stream_file_with_an_empty_string() {

        new JavaStreamFile("");
    }

    @Test(expected = RuntimeException.class)
    public void I_cannot_probe_the_mime_type_of_an_invalid_stream_file() throws IOException {

        final java.io.File file = mock(java.io.File.class);
        when(file.getCanonicalPath()).thenThrow(new IOException("text probe mime type exception."));

        JavaStreamFile.probeMimeType(file);
    }

    @Test
    public void I_can_get_a_stream_files_name() {

        The_files_name_should_be_correct(new JavaStreamFileCreator());
    }

    @Test
    public void I_can_get_a_stream_files_modification_date() {

        The_files_modification_date_should_be_correct(new JavaStreamFileCreator());
    }

    @Test
    public void I_can_get_a_stream_files_extension() {

        The_files_extension_should_be_correct(new JavaStreamFileCreator());
    }

    @Test
    public void I_can_get_a_stream_files_size() {

        The_files_size_should_be_correct(new JavaStreamFileCreator());
    }

    @Test
    public void I_can_get_a_stream_files_mime_type() {

        The_files_mime_type_should_be_correct(new JavaStreamFileCreator());
    }

    @Test
    public void I_can_check_the_equality_of_a_stream_file() {

        The_file_should_have_correct_equality(new JavaStreamFileCreator());
    }

    @Test
    public void I_can_to_string_a_stream_file() {

        The_file_should_have_the_correct_to_string_value(new JavaStreamFileCreator());
    }

    @Test(expected = JsonMappingException.class)
    public void I_cannot_serialise_a_stream_file() throws JsonProcessingException {

        The_file_should_be_able_to_be_serialised(new JavaStreamFileCreator());
    }

    @Test
    public void I_can_get_a_stream_files_input_stream() {

        The_file_should_produce_an_input_stream(new JavaStreamFileCreator());
    }

    @Test
    public void I_can_read_a_stream_files_input_stream() {

        The_files_input_stream_should_contain(new JavaStreamFileCreator());
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_read_an_invalid_files_input_stream() throws IOException {

        getInputStream(new java.io.File(FILE_ONE.getAbsolutePath() + '\u0000'));
    }

    private static class JavaStreamFileCreator extends AbstractFileCreator<StreamFile>
            implements StreamFileCreator<StreamFile> {

        @Override
        public StreamFile create(String path) {
            return new JavaStreamFile(path);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R extends File> Class<R> type() {
            return (Class<R>) StreamFile.class;
        }
    }
}
