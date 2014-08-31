package shiver.me.timbers.file.io;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.DirectorySteps.The_directories_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.DirectorySteps.The_directories_name_should_be_correct;
import static shiver.me.timbers.file.io.DirectorySteps.The_directory_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.DirectorySteps.The_directory_should_have_correct_equality;
import static shiver.me.timbers.file.io.DirectorySteps.The_directory_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSteps.The_file_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileSteps.The_files_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSteps.The_files_name_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_correct_equality;

public class JavaFileSystemElementTest {

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_java_file_system_element_with_a_null_path() {

        new JavaFileSystemElement((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_java_file_system_element_with_a_null_file() {

        new JavaFileSystemElement((java.io.File) null);
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_file_system_element_with_a_path_to_non_existent_file() {

        new JavaFileSystemElement("this/file/does/not/exist");
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_file_system_element_with_an_invalid_path() throws IOException {

        final java.io.File file = mock(java.io.File.class);
        when(file.getCanonicalFile()).thenThrow(new IOException());

        new JavaFileSystemElement(file);
    }

    @Test
    public void I_can_create_a_java_file_system_element_with_a_root_and_path() {

        new JavaFileSystemElement(CURRENT_DIRECTORY_ABSOLUTE_PATH, FILE_ONE.getPath());
    }

    @Test
    public void I_can_create_a_java_file_system_element_with_a_dot() {

        new JavaFileSystemElement(".");
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_java_file_system_element_with_an_empty_string() {

        new JavaFileSystemElement("");
    }

    @Test
    public void I_can_get_a_files_name() {

        The_files_name_should_be_correct(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_get_a_directories_name() {

        The_directories_name_should_be_correct(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_get_a_files_modification_date() {

        The_files_modification_date_should_be_correct(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_get_a_directories_modification_date() {

        The_directories_modification_date_should_be_correct(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_check_the_equality_of_a_file() {

        The_file_system_element_should_have_correct_equality(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_check_the_equality_of_a_directory() {

        The_directory_should_have_correct_equality(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_to_string_a_file() {

        The_file_should_have_the_correct_to_string_value(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_to_string_a_directory() {

        The_directory_should_have_the_correct_to_string_value(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_serialise_a_file() throws JsonProcessingException {

        The_file_should_be_able_to_be_serialised(new JavaFileSystemElementCreator());
    }

    @Test
    public void I_can_serialise_a_directory() throws JsonProcessingException {

        The_directory_should_be_able_to_be_serialised(new JavaFileSystemElementCreator());
    }

    private static class JavaFileSystemElementCreator implements FileSystemElementCreator {

        @Override
        public FileSystemElement create(String path) {
            return new JavaFileSystemElement(path);
        }

        @Override
        public FileSystemElement mock(String name, Date modified) {

            final FileSystemElement mock = Mockito.mock(FileSystemElement.class);
            when(mock.getName()).thenReturn(name);
            when(mock.getModified()).thenReturn(modified);

            return mock;
        }
    }
}
