package shiver.me.timbers.file.io;

import com.fasterxml.jackson.core.JsonProcessingException;

import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.DirectoryConstants.CURRENT_DIRECTORY;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_FOUR;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_THREE;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_TWO;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_be_able_to_be_serialised;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_correct_equality;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_element_should_have_the_correct_to_string_value;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_modification_date_should_be_correct;
import static shiver.me.timbers.file.io.FileSystemElementSteps.The_file_system_elements_name_should_be_correct;

public class DirectorySteps {

    public static void The_directories_name_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_name_should_be_correct(CURRENT_DIRECTORY,
                creator.create(CURRENT_DIRECTORY.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(DIRECTORY_ONE, creator.create(DIRECTORY_ONE.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(DIRECTORY_TWO, creator.create(DIRECTORY_TWO.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(DIRECTORY_THREE,
                creator.create(DIRECTORY_THREE.getAbsolutePath()));
        The_file_system_elements_name_should_be_correct(DIRECTORY_FOUR,
                creator.create(DIRECTORY_FOUR.getAbsolutePath()));
    }

    public static void The_directories_modification_date_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_modification_date_should_be_correct(CURRENT_DIRECTORY,
                creator.create(CURRENT_DIRECTORY.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_ONE,
                creator.create(DIRECTORY_ONE.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_TWO,
                creator.create(DIRECTORY_TWO.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_THREE,
                creator.create(DIRECTORY_THREE.getAbsolutePath()));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_FOUR,
                creator.create(DIRECTORY_FOUR.getAbsolutePath()));
    }

    public static void The_directory_should_have_correct_equality(FileSystemElementCreator creator) {

        The_file_system_element_should_have_correct_equality(creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH),
                creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH), creator);
    }

    public static void The_directory_should_have_the_correct_to_string_value(FileSystemElementCreator creator) {

        The_file_system_element_should_have_the_correct_to_string_value(CURRENT_DIRECTORY,
                creator.create(CURRENT_DIRECTORY.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_ONE,
                creator.create(DIRECTORY_ONE.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_TWO,
                creator.create(DIRECTORY_TWO.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_THREE,
                creator.create(DIRECTORY_THREE.getAbsolutePath()));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_FOUR,
                creator.create(DIRECTORY_FOUR.getAbsolutePath()));
    }

    public static void The_directory_should_be_able_to_be_serialised(FileSystemElementCreator creator)
            throws JsonProcessingException {

        The_file_system_element_should_be_able_to_be_serialised(creator.create(CURRENT_DIRECTORY.getAbsolutePath()),
                CURRENT_DIRECTORY.getName(), "", Long.toString(CURRENT_DIRECTORY.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(DIRECTORY_ONE.getAbsolutePath()),
                DIRECTORY_ONE.getName(), "", Long.toString(DIRECTORY_ONE.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(DIRECTORY_TWO.getAbsolutePath()),
                DIRECTORY_TWO.getName(), "", Long.toString(DIRECTORY_TWO.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(DIRECTORY_THREE.getAbsolutePath()),
                DIRECTORY_THREE.getName(), "", Long.toString(DIRECTORY_THREE.getModified().getTime()));
        The_file_system_element_should_be_able_to_be_serialised(creator.create(DIRECTORY_FOUR.getAbsolutePath()),
                DIRECTORY_FOUR.getName(), "", Long.toString(DIRECTORY_FOUR.getModified().getTime()));
    }
}
