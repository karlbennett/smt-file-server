package shiver.me.timbers.file.io;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_FOUR_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_THREE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_TWO_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_FOUR_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_THREE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_TWO_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.buildPath;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_directories_extension_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_directories_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_directories_name_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_directory_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_directory_should_have_correct_equality;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_directory_should_have_the_correct_to_string_value;

public class JavaDirectoryTest {

    private static final Directory CURRENT_DIRECTORY = new JavaDirectory(CURRENT_DIRECTORY_ABSOLUTE_PATH);
    private static final Directory DIRECTORY_ONE = new JavaDirectory(DIRECTORY_ONE_ABSOLUTE_PATH);
    private static final Directory DIRECTORY_TWO = new JavaDirectory(DIRECTORY_TWO_ABSOLUTE_PATH);
    private static final Directory DIRECTORY_THREE = new JavaDirectory(DIRECTORY_THREE_ABSOLUTE_PATH);
    private static final Directory DIRECTORY_FOUR = new JavaDirectory(DIRECTORY_FOUR_ABSOLUTE_PATH);
    private static final Directory SHIVER_DIRECTORY = new JavaDirectory(buildPath(CURRENT_DIRECTORY_ABSOLUTE_PATH,
            "shiver"));

    private static final File FILE_ONE = new JavaFile(FILE_ONE_ABSOLUTE_PATH);
    private static final File FILE_TWO = new JavaFile(FILE_TWO_ABSOLUTE_PATH);
    private static final File FILE_THREE = new JavaFile(FILE_THREE_ABSOLUTE_PATH);
    private static final File FILE_FOUR = new JavaFile(FILE_FOUR_ABSOLUTE_PATH);

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_directory_with_a_null_path() {

        new JavaDirectory((String) null);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_create_a_directory_with_a_null_file() {

        new JavaDirectory((java.io.File) null);
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_directory_with_a_path_to_non_existent_file() {

        new JavaDirectory("this/file/does/not/exist");
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_directory_with_an_invalid_path() throws IOException {

        final java.io.File file = mock(java.io.File.class);
        when(file.getCanonicalFile()).thenThrow(new IOException());

        new JavaDirectory(file);
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_directory_with_an_element_that_does_not_list_any_files() throws IOException {

        final java.io.File file = mock(java.io.File.class);
        when(file.getCanonicalFile()).thenReturn(file);
        when(file.exists()).thenReturn(true);

        new JavaDirectory(file);
    }

    @Test
    public void I_can_create_a_java_file_system_element_with_a_root_and_path() {

        new JavaDirectory(CURRENT_DIRECTORY_ABSOLUTE_PATH, DIRECTORY_ONE_NAME);
    }

    @Test
    public void I_can_create_a_directory_with_a_dot() {

        new JavaDirectory(".");
    }

    @Test
    public void I_can_create_a_directory_with_an_empty_string() {

        new JavaDirectory("");
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_create_a_directory_with_a_file() {

        new JavaDirectory(FILE_ONE_ABSOLUTE_PATH);
    }

    @Test
    public void I_can_get_a_directories_name() {

        The_directories_name_should_be_correct(new JavaDirectoryCreator());
    }

    @Test
    public void I_can_get_a_directories_extension() {

        The_directories_extension_should_be_correct(new JavaDirectoryCreator());
    }

    @Test
    public void I_can_get_a_directories_modification_date() {

        The_directories_modification_date_should_be_correct(new JavaDirectoryCreator());
    }

    @Test
    public void I_can_check_the_equality_of_a_directory() {

        The_directory_should_have_correct_equality(new JavaDirectoryCreator());
    }

    @Test
    public void I_can_to_string_a_directory() {

        The_directory_should_have_the_correct_to_string_value(new JavaDirectoryCreator());
    }

    @Test
    public void I_can_get_a_directories_directory_list() {

        The_directories_list_of_directories_should_be_correct(CURRENT_DIRECTORY, DIRECTORY_ONE, DIRECTORY_THREE,
                SHIVER_DIRECTORY);
        The_directories_list_of_directories_should_be_correct(DIRECTORY_ONE, DIRECTORY_TWO);
        The_directories_list_of_directories_should_be_correct(DIRECTORY_TWO);
        The_directories_list_of_directories_should_be_correct(DIRECTORY_THREE, DIRECTORY_FOUR);
        The_directories_list_of_directories_should_be_correct(DIRECTORY_FOUR);
    }

    @Test
    public void I_can_get_a_directories_file_list() {

        The_directories_list_of_files_should_be_correct(CURRENT_DIRECTORY, FILE_ONE);
        The_directories_list_of_files_should_be_correct(DIRECTORY_ONE, FILE_TWO);
        The_directories_list_of_files_should_be_correct(DIRECTORY_TWO, FILE_THREE);
        The_directories_list_of_files_should_be_correct(DIRECTORY_THREE);
        The_directories_list_of_files_should_be_correct(DIRECTORY_FOUR, FILE_FOUR);
    }

    private static void The_directories_list_of_directories_should_be_correct(Directory directory,
                                                                              FileSystemElement... directories) {

        assertThat("the list of directories should be correct.", directory.getDirectories(), contains(directories));
    }

    private static void The_directories_list_of_directories_should_be_correct(Directory directory) {

        assertThat("the list of directories should be empty.", directory.getDirectories(), empty());
    }

    private static void The_directories_list_of_files_should_be_correct(Directory directory,
                                                                        File... files) {

        assertThat("the list of files should be correct.", directory.getFiles(), contains(files));
    }

    private static void The_directories_list_of_files_should_be_correct(Directory directory) {

        assertThat("the list of files should be empty.", directory.getFiles(), empty());
    }

    @Test
    public void I_can_serialise_a_directory() {

        The_directory_should_be_able_to_be_serialised(new JavaDirectoryCreator());
    }

    private static class JavaDirectoryCreator implements FileSystemElementCreator {

        @Override
        public FileSystemElement create(String path) {
            return new JavaDirectory(path);
        }
    }
}
