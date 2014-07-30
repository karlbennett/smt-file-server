package shiver.me.timbers.file.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE;
import static shiver.me.timbers.file.io.FileConstants.FILE_FOUR;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX;
import static shiver.me.timbers.file.io.FileConstants.FILE_THREE;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_correct_equality;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_name_should_be_correct;

public class FileSteps {

    public static void The_files_name_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_name_should_be_correct(FILE_ONE, creator.create(FILE_ONE.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(FILE_TWO, creator.create(FILE_TWO.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(FILE_THREE, creator.create(FILE_THREE.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(FILE_FOUR, creator.create(FILE_FOUR.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(FILE_FIVE, creator.create(FILE_FIVE.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(FILE_SIX, creator.create(FILE_SIX.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(FILE_SEVEN, creator.create(FILE_SEVEN.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(FILE_EIGHT, creator.create(FILE_EIGHT.getAbsolutePath()));
    }

    public static void The_files_extension_should_be_correct(FileCreator creator) {

        The_files_extension_should_be_correct(FILE_ONE.getExtension(), creator.create(FILE_ONE.getAbsolutePath()));
        The_files_extension_should_be_correct(FILE_TWO.getExtension(), creator.create(FILE_TWO.getAbsolutePath()));
        The_files_extension_should_be_correct(FILE_THREE.getExtension(), creator.create(FILE_THREE.getAbsolutePath()));
        The_files_extension_should_be_correct(FILE_FOUR.getExtension(), creator.create(FILE_FOUR.getAbsolutePath()));
        The_files_extension_should_be_correct(FILE_FIVE.getExtension(), creator.create(FILE_FIVE.getAbsolutePath()));
        The_files_extension_should_be_correct(FILE_SIX.getExtension(), creator.create(FILE_SIX.getAbsolutePath()));
        The_files_extension_should_be_correct(FILE_SEVEN.getExtension(), creator.create(FILE_SEVEN.getAbsolutePath()));
        The_files_extension_should_be_correct(FILE_EIGHT.getExtension(), creator.create(FILE_EIGHT.getAbsolutePath()));
    }


    public static void The_files_extension_should_be_correct(String extension, File file) {

        assertEquals("the extension of the file should be correct.", extension, file.getExtension());
    }


    public static void The_files_modification_date_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_modification_date_should_be_correct(FILE_ONE,
                creator.create(FILE_ONE.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(FILE_TWO,
                creator.create(FILE_TWO.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(FILE_THREE,
                creator.create(FILE_THREE.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(FILE_FOUR,
                creator.create(FILE_FOUR.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(FILE_FIVE,
                creator.create(FILE_FIVE.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(FILE_SIX,
                creator.create(FILE_SIX.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(FILE_SEVEN,
                creator.create(FILE_SEVEN.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(FILE_EIGHT,
                creator.create(FILE_EIGHT.getAbsolutePath()));
    }

    public static void The_file_should_have_correct_equality(FileCreator creator) {

        The_file_should_have_correct_equality(creator.create(FILE_ONE.getAbsolutePath()),
                creator.create(FILE_ONE.getAbsolutePath()));
    }

    public static void The_file_should_have_correct_equality(File left, final File right) {

        The_file_system_element_should_have_correct_equality(left, right);

        final File mock = mock(File.class);
        when(mock.getName()).thenReturn(left.getName());
        when(mock.getModified()).thenReturn(left.getModified());
        when(mock.getExtension()).thenReturn("different");

        assertNotEquals("the file should not be equal to a file with a different extension.", left, mock);
    }

    public static void The_file_should_have_the_correct_to_string_value(FileSystemElementCreator creator) {

        The_file_system_element_should_have_the_correct_to_string_value(FILE_ONE,
                creator.create(FILE_ONE.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_TWO,
                creator.create(FILE_TWO.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_THREE,
                creator.create(FILE_THREE.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_FOUR,
                creator.create(FILE_FOUR.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_FIVE,
                creator.create(FILE_FIVE.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_SIX,
                creator.create(FILE_SIX.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_SEVEN,
                creator.create(FILE_SEVEN.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_EIGHT,
                creator.create(FILE_EIGHT.getAbsolutePath()));
    }

    public static void The_file_should_be_able_to_be_serialised(FileSystemElementCreator creator) {

        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_ONE.getAbsolutePath()),
                FILE_ONE.getName(), FILE_ONE.getExtension(), Long.toString(FILE_ONE.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_TWO.getAbsolutePath()),
                FILE_TWO.getName(), FILE_TWO.getExtension(), Long.toString(FILE_TWO.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_THREE.getAbsolutePath()),
                FILE_THREE.getName(), FILE_THREE.getExtension(), Long.toString(FILE_THREE.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_FOUR.getAbsolutePath()),
                FILE_FOUR.getName(), FILE_FOUR.getExtension(), Long.toString(FILE_FOUR.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_FIVE.getAbsolutePath()),
                FILE_FIVE.getName(), FILE_FIVE.getExtension(), Long.toString(FILE_FIVE.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_SIX.getAbsolutePath()),
                FILE_SIX.getName(), FILE_SIX.getExtension(), Long.toString(FILE_SIX.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_SEVEN.getAbsolutePath()),
                FILE_SEVEN.getName(), FILE_SEVEN.getExtension(), Long.toString(FILE_SEVEN.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_EIGHT.getAbsolutePath()),
                FILE_EIGHT.getName(), FILE_EIGHT.getExtension(), Long.toString(FILE_EIGHT.getModified().getTime()));
    }
}
