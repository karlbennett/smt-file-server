package shiver.me.timbers.file.io;

import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_NAME;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_FOUR_ABSOLUTE_PATH;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_FOUR_MODIFICATION_DATE;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_FOUR_NAME;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_ONE_MODIFICATION_DATE;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_ONE_NAME;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_THREE_ABSOLUTE_PATH;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_THREE_MODIFICATION_DATE;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_THREE_NAME;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_TWO_ABSOLUTE_PATH;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_TWO_MODIFICATION_DATE;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_TWO_NAME;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_correct_equality;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_extension_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_name_should_be_correct;

public class DirectorySteps {

    public static void The_directories_name_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_name_should_be_correct(CURRENT_DIRECTORY_NAME,
                creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(DIRECTORY_ONE_NAME,
                creator.create(DIRECTORY_ONE_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(DIRECTORY_TWO_NAME,
                creator.create(DIRECTORY_TWO_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(DIRECTORY_THREE_NAME,
                creator.create(DIRECTORY_THREE_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(DIRECTORY_FOUR_NAME,
                creator.create(DIRECTORY_FOUR_ABSOLUTE_PATH));
    }

    public static void The_directories_extension_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_extension_should_be_correct("", creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_ONE_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_TWO_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_THREE_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_FOUR_ABSOLUTE_PATH));
    }

    public static void The_directories_modification_date_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_modification_date_should_be_correct(CURRENT_DIRECTORY_MODIFICATION_DATE,
                creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_ONE_MODIFICATION_DATE,
                creator.create(DIRECTORY_ONE_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_TWO_MODIFICATION_DATE,
                creator.create(DIRECTORY_TWO_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_THREE_MODIFICATION_DATE,
                creator.create(DIRECTORY_THREE_ABSOLUTE_PATH));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_FOUR_MODIFICATION_DATE,
                creator.create(DIRECTORY_FOUR_ABSOLUTE_PATH));
    }

    public static void The_directory_should_have_correct_equality(FileSystemElementCreator creator) {

        The_file_system_element_should_have_correct_equality(creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH),
                creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH));
    }

    public static void The_directory_should_have_the_correct_to_string_value(FileSystemElementCreator creator) {

        The_file_system_element_should_have_the_correct_to_string_value(CURRENT_DIRECTORY_NAME,
                creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_ONE_NAME,
                creator.create(DIRECTORY_ONE_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_TWO_NAME,
                creator.create(DIRECTORY_TWO_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_THREE_NAME,
                creator.create(DIRECTORY_THREE_ABSOLUTE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_FOUR_NAME,
                creator.create(DIRECTORY_FOUR_ABSOLUTE_PATH));
    }

    public static void The_directory_should_be_able_to_be_serialised(FileSystemElementCreator creator) {

        The_file_system_element_should_be_able_to_be_serialised(creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH),
                CURRENT_DIRECTORY_NAME, "", Long.toString(CURRENT_DIRECTORY_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(DIRECTORY_ONE_ABSOLUTE_PATH),
                DIRECTORY_ONE_NAME, "", Long.toString(DIRECTORY_ONE_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(DIRECTORY_TWO_ABSOLUTE_PATH),
                DIRECTORY_TWO_NAME, "", Long.toString(DIRECTORY_TWO_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(DIRECTORY_THREE_ABSOLUTE_PATH),
                DIRECTORY_THREE_NAME, "", Long.toString(DIRECTORY_THREE_MODIFICATION_DATE.getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(DIRECTORY_FOUR_ABSOLUTE_PATH),
                DIRECTORY_FOUR_NAME, "", Long.toString(DIRECTORY_FOUR_MODIFICATION_DATE.getTime()));
    }
}
