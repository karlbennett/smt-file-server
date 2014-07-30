package shiver.me.timbers.file.io;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_FOUR;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_THREE;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_TWO;
import static shiver.me.timbers.file.io.DirectoryConstants.SHIVER_DIRECTORY;
import static shiver.me.timbers.file.io.DirectoryConstants.TEST_DIRECTORY;
import static shiver.me.timbers.file.io.DirectorySteps.The_directories_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.DirectorySteps.The_directories_name_should_be_correct;
import static shiver.me.timbers.file.io.DirectorySteps.The_directory_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.DirectorySteps.The_directory_should_have_correct_equality;
import static shiver.me.timbers.file.io.DirectorySteps.The_directory_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE;
import static shiver.me.timbers.file.io.FileConstants.FILE_FOUR;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX;
import static shiver.me.timbers.file.io.FileConstants.FILE_THREE;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO;
import static shiver.me.timbers.file.io.FileConstants.TEST_PROPERTIES_FILE;

public class JavaDirectoryTest {

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

        new JavaDirectory(CURRENT_DIRECTORY_ABSOLUTE_PATH, DIRECTORY_ONE.getPath());
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

        new JavaDirectory(FILE_ONE.getAbsolutePath());
    }

    @Test
    public void I_can_get_a_directories_name() {

        The_directories_name_should_be_correct(new JavaDirectoryCreator());
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

        The_directories_list_of_directories_should_be_correct(new JavaDirectory(TEST_DIRECTORY.getAbsolutePath()),
                DIRECTORY_ONE, DIRECTORY_THREE, SHIVER_DIRECTORY);
        The_directories_list_of_directories_should_be_correct(new JavaDirectory(DIRECTORY_ONE.getAbsolutePath()),
                DIRECTORY_TWO);
        The_directories_list_of_directories_should_be_correct(new JavaDirectory(DIRECTORY_TWO.getAbsolutePath()));
        The_directories_list_of_directories_should_be_correct(new JavaDirectory(DIRECTORY_THREE.getAbsolutePath()),
                DIRECTORY_FOUR);
        The_directories_list_of_directories_should_be_correct(new JavaDirectory(DIRECTORY_FOUR.getAbsolutePath()));
    }

    @Test
    public void I_can_get_a_directories_file_list() {

        The_directories_list_of_files_should_be_correct(new JavaDirectory(TEST_DIRECTORY.getAbsolutePath()),
                TEST_PROPERTIES_FILE, FILE_ONE, FILE_FIVE, FILE_SIX, FILE_SEVEN, FILE_EIGHT);
        The_directories_list_of_files_should_be_correct(new JavaDirectory(DIRECTORY_ONE.getAbsolutePath()), FILE_TWO);
        The_directories_list_of_files_should_be_correct(new JavaDirectory(DIRECTORY_TWO.getAbsolutePath()), FILE_THREE);
        The_directories_list_of_files_should_be_correct(new JavaDirectory(DIRECTORY_THREE.getAbsolutePath()));
        The_directories_list_of_files_should_be_correct(new JavaDirectory(DIRECTORY_FOUR.getAbsolutePath()), FILE_FOUR);
    }

    private static void The_directories_list_of_directories_should_be_correct(Directory directory,
                                                                              FileSystemElement... directories) {

        assertThat("the list of directories should be correct.", directory.getDirectories(),
                containsInAnyOrder(directories));
    }

    private static void The_directories_list_of_directories_should_be_correct(Directory directory) {

        assertThat("the list of directories should be empty.", directory.getDirectories(), empty());
    }

    private static void The_directories_list_of_files_should_be_correct(Directory directory,
                                                                        File... files) {

        assertThat("the list of files should be correct.", directory.getFiles(), containsInAnyOrder(files));
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
