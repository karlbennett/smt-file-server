package shiver.me.timbers.file.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT_EXTENSION;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT_MODIFICATION_DATE;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT_NAME;
import static shiver.me.timbers.file.io.FileConstants.FILE_EXTENSION;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE_EXTENSION;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE_MODIFICATION_DATE;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE_NAME;
import static shiver.me.timbers.file.io.FileConstants.FILE_FOUR_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_FOUR_MODIFICATION_DATE;
import static shiver.me.timbers.file.io.FileConstants.FILE_FOUR_NAME;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE_MODIFICATION_DATE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE_NAME;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN_EXTENSION;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN_MODIFICATION_DATE;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN_NAME;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX_EXTENSION;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX_MODIFICATION_DATE;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX_NAME;
import static shiver.me.timbers.file.io.FileConstants.FILE_THREE_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_THREE_MODIFICATION_DATE;
import static shiver.me.timbers.file.io.FileConstants.FILE_THREE_NAME;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO_MODIFICATION_DATE;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO_NAME;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_correct_equality;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_name_should_be_correct;

public class FileSteps {

    public static void The_files_name_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_name_should_be_correct(FILE_ONE_NAME, creator.create(FILE_ONE_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_TWO_NAME, creator.create(FILE_TWO_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_THREE_NAME, creator.create(FILE_THREE_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_FOUR_NAME, creator.create(FILE_FOUR_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_FIVE_NAME, creator.create(FILE_FIVE_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_SIX_NAME, creator.create(FILE_SIX_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_SEVEN_NAME, creator.create(FILE_SEVEN_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_EIGHT_NAME, creator.create(FILE_EIGHT_ABSOLUTE_PATH));
    }

    public static void The_files_extension_should_be_correct(FileCreator creator) {

        The_files_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_ONE_ABSOLUTE_PATH));
        The_files_extension_should_be_correct("", creator.create(FILE_TWO_ABSOLUTE_PATH));
        The_files_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_THREE_ABSOLUTE_PATH));
        The_files_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_FOUR_ABSOLUTE_PATH));
        The_files_extension_should_be_correct(FILE_FIVE_EXTENSION, creator.create(FILE_FIVE_ABSOLUTE_PATH));
        The_files_extension_should_be_correct(FILE_SIX_EXTENSION, creator.create(FILE_SIX_ABSOLUTE_PATH));
        The_files_extension_should_be_correct(FILE_SEVEN_EXTENSION, creator.create(FILE_SEVEN_ABSOLUTE_PATH));
        The_files_extension_should_be_correct(FILE_EIGHT_EXTENSION, creator.create(FILE_EIGHT_ABSOLUTE_PATH));
    }


    public static void The_files_extension_should_be_correct(String extension, File file) {

        assertEquals("the extension of the file should be correct.", extension, file.getExtension());
    }


    public static void The_files_modification_date_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_modification_date_should_be_correct(FILE_ONE_MODIFICATION_DATE,
                creator.create(FILE_ONE_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_TWO_MODIFICATION_DATE,
                creator.create(FILE_TWO_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_THREE_MODIFICATION_DATE,
                creator.create(FILE_THREE_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_FOUR_MODIFICATION_DATE,
                creator.create(FILE_FOUR_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_FIVE_MODIFICATION_DATE,
                creator.create(FILE_FIVE_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_SIX_MODIFICATION_DATE,
                creator.create(FILE_SIX_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_SEVEN_MODIFICATION_DATE,
                creator.create(FILE_SEVEN_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_EIGHT_MODIFICATION_DATE,
                creator.create(FILE_EIGHT_ABSOLUTE_PATH));
    }

    public static void The_file_should_have_correct_equality(FileCreator creator) {

        The_file_should_have_correct_equality(creator.create(FILE_ONE_ABSOLUTE_PATH),
                creator.create(FILE_ONE_ABSOLUTE_PATH));
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

        The_file_system_element_should_have_the_correct_to_string_value(FILE_ONE_NAME,
                creator.create(FILE_ONE_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_TWO_NAME,
                creator.create(FILE_TWO_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_THREE_NAME,
                creator.create(FILE_THREE_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_FOUR_NAME,
                creator.create(FILE_FOUR_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_FIVE_NAME,
                creator.create(FILE_FIVE_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_SIX_NAME,
                creator.create(FILE_SIX_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_SEVEN_NAME,
                creator.create(FILE_SEVEN_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_EIGHT_NAME,
                creator.create(FILE_EIGHT_ABSOLUTE_PATH));
    }

    public static void The_file_should_be_able_to_be_serialised(FileSystemElementCreator creator) {

        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_ONE_ABSOLUTE_PATH), FILE_ONE_NAME,
                FILE_EXTENSION, Long.toString(FILE_ONE_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_TWO_ABSOLUTE_PATH), FILE_TWO_NAME,
                "", Long.toString(FILE_TWO_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_THREE_ABSOLUTE_PATH),
                FILE_THREE_NAME, FILE_EXTENSION, Long.toString(FILE_THREE_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_FOUR_ABSOLUTE_PATH),
                FILE_FOUR_NAME, FILE_EXTENSION, Long.toString(FILE_FOUR_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_FIVE_ABSOLUTE_PATH),
                FILE_FIVE_NAME, FILE_FIVE_EXTENSION, Long.toString(FILE_FIVE_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_SIX_ABSOLUTE_PATH),
                FILE_SIX_NAME, FILE_SIX_EXTENSION, Long.toString(FILE_SIX_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_SEVEN_ABSOLUTE_PATH),
                FILE_SEVEN_NAME, FILE_SEVEN_EXTENSION, Long.toString(FILE_SEVEN_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(FILE_EIGHT_ABSOLUTE_PATH),
                FILE_EIGHT_NAME, FILE_EIGHT_EXTENSION, Long.toString(FILE_EIGHT_MODIFICATION_DATE.getTime()));
    }
}
