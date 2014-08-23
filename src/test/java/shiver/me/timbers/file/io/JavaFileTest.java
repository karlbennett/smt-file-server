package shiver.me.timbers.file.io;

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
import static shiver.me.timbers.file.io.FileSteps.The_file_should_produce_an_input_stream;
import static shiver.me.timbers.file.io.FileSteps.The_files_extension_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_input_stream_should_contain;
import static shiver.me.timbers.file.io.FileSteps.The_files_mime_type_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_name_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_size_should_be_correct;
import static shiver.me.timbers.file.io.JavaFile.getInputStream;

public class JavaFileTest {

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_java_file_with_a_null_path() {

        new JavaFile((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_java_file_with_a_null_file() {

        new JavaFile((java.io.File) null);
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_file_with_a_path_to_a_directory() {

        new JavaFile(DIRECTORY_ONE.getAbsolutePath());
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_file_with_a_path_to_non_existent_file() {

        new JavaFile("this/file/does/not/exist");
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_file_with_an_invalid_path() throws IOException {

        final java.io.File file = mock(java.io.File.class);
        when(file.getCanonicalFile()).thenThrow(new IOException());

        new JavaFile(file);
    }

    @Test
    public void I_can_create_a_java_file_with_a_root_and_path() {

        new JavaFile(CURRENT_DIRECTORY.getAbsolutePath(), FILE_ONE.getPath());
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_file_with_a_dot() {

        new JavaFile(".");
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_file_with_an_empty_string() {

        new JavaFile("");
    }

    @Test(expected = RuntimeException.class)
    public void I_cannot_probe_the_mime_type_of_an_invalid_file() throws IOException {

        final java.io.File file = mock(java.io.File.class);
        when(file.getCanonicalPath()).thenThrow(new IOException("text probe mime type exception."));

        JavaFile.probeMimeType(file);
    }

    @Test
    public void I_can_get_a_files_name() {

        The_files_name_should_be_correct(new JavaFileCreator());
    }

    @Test
    public void I_can_get_a_files_modification_date() {

        The_files_modification_date_should_be_correct(new JavaFileCreator());
    }

    @Test
    public void I_can_get_a_files_extension() {

        The_files_extension_should_be_correct(new JavaFileCreator());
    }

    @Test
    public void I_can_get_a_files_size() {

        The_files_size_should_be_correct(new JavaFileCreator());
    }

    @Test
    public void I_can_get_a_files_mime_type() {

        The_files_mime_type_should_be_correct(new JavaFileCreator());
    }

    @Test
    public void I_can_check_the_equality_of_a_file() {

        The_file_should_have_correct_equality(new JavaFileCreator());
    }

    @Test
    public void I_can_to_string_a_file() {

        The_file_should_have_the_correct_to_string_value(new JavaFileCreator());
    }

    @Test
    public void I_can_serialise_a_file() {

        The_file_should_be_able_to_be_serialised(new JavaFileCreator());
    }

    @Test
    public void I_can_get_a_files_input_stream() {

        The_file_should_produce_an_input_stream(new JavaFileCreator());
    }

    @Test
    public void I_can_read_a_files_input_stream() {

        The_files_input_stream_should_contain(new JavaFileCreator());
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_read_an_invalid_files_input_stream() throws IOException {

        getInputStream(new java.io.File(FILE_ONE.getAbsolutePath() + '\u0000'));
    }

    private static class JavaFileCreator extends AbstractFileCreator {

        @Override
        public File create(String path) {
            return new JavaFile(path);
        }
    }
}
