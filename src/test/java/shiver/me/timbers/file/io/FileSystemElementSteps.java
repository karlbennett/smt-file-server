package shiver.me.timbers.file.io;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_NAME;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_FOUR_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_FOUR_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_FOUR_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_THREE_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_THREE_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_THREE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_TWO_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_TWO_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_TWO_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_EXTENSION;
import static shiver.me.timbers.Constants.FILE_FOUR_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.FILE_FOUR_NAME;
import static shiver.me.timbers.Constants.FILE_FOUR_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_ONE_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.FILE_ONE_NAME;
import static shiver.me.timbers.Constants.FILE_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_THREE_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.FILE_THREE_NAME;
import static shiver.me.timbers.Constants.FILE_THREE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_TWO_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.FILE_TWO_NAME;
import static shiver.me.timbers.Constants.FILE_TWO_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.OBJECT_MAPPER;

public class FileSystemElementSteps {

    public static void The_files_name_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_name_should_be_correct(FILE_ONE_NAME, creator.create(FILE_ONE_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_TWO_NAME, creator.create(FILE_TWO_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_THREE_NAME, creator.create(FILE_THREE_ABSOLUTE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_FOUR_NAME, creator.create(FILE_FOUR_ABSOLUTE_PATH));
    }

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

    public static void The_file_system_elements_name_should_be_correct(String name, FileSystemElement element) {

        assertEquals("the name of the file system element should be correct.", name, element.getName());
    }

    public static void The_files_extension_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_ONE_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(FILE_TWO_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_THREE_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_FOUR_ABSOLUTE_PATH));
    }

    public static void The_directories_extension_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_extension_should_be_correct("", creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_ONE_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_TWO_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_THREE_ABSOLUTE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_FOUR_ABSOLUTE_PATH));
    }

    public static void The_file_system_elements_extension_should_be_correct(String extension,
                                                                            FileSystemElement element) {

        assertEquals("the extension of the file system element should be correct.", extension, element.getExtension());
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

    public static void The_file_system_elements_modification_date_should_be_correct(Date modified,
                                                                                    FileSystemElement element) {

        assertEquals("the modification date of the file system element should be correct.", modified,
                element.getModified());
    }

    public static void The_file_should_have_correct_equality(FileSystemElementCreator creator) {

        The_file_system_element_should_have_correct_equality(creator.create(FILE_ONE_ABSOLUTE_PATH),
                creator.create(FILE_ONE_ABSOLUTE_PATH));
    }

    public static void The_directory_should_have_correct_equality(FileSystemElementCreator creator) {

        The_file_system_element_should_have_correct_equality(creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH),
                creator.create(CURRENT_DIRECTORY_ABSOLUTE_PATH));
    }

    public static void The_file_system_element_should_have_correct_equality(FileSystemElement left,
                                                                            final FileSystemElement right) {

        assertEquals("the file system element should be equal to it's self.", left, left);

        assertEquals("the file system elements should be equal.", left, right);

        assertEquals("the file system element hash codes should be equal.", left.hashCode(), right.hashCode());

        assertNotEquals("the file system element should not be equal to an element with a different name.", left,
                mockFileSystemElement("different", left.getExtension(), left.getModified()));

        assertNotEquals("the file system element should not be equal to an element with a different extension.", left,
                mockFileSystemElement(left.getName(), "different", left.getModified()));

        assertNotEquals("the file system element should not be equal to an element with a different modified date.",
                left, mockFileSystemElement(left.getName(), left.getExtension(), new Date()));

        assertNotEquals("the file system element should not be equal to object.", left, new Object());

        assertNotEquals("the file system element should not be equal to null.", left, null);
    }

    private static FileSystemElement mockFileSystemElement(String name, String extension, Date modified) {

        final FileSystemElement mock = mock(FileSystemElement.class);
        when(mock.getName()).thenReturn(name);
        when(mock.getExtension()).thenReturn(extension);
        when(mock.getModified()).thenReturn(modified);

        return mock;
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

    public static void The_file_system_element_should_have_the_correct_to_string_value(String string,
                                                                                       FileSystemElement element) {

        assertEquals("the file system elements toString value should be correct.", string, element.toString());
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

    public static void The_file_system_element_should_be_able_to_be_serialised(FileSystemElement element,
                                                                               String... values) {

        try {

            for (String value : values) {

                assertThat(format("the file system element %s could not be serialised.", element),
                        OBJECT_MAPPER.writeValueAsString(element), containsString(value));
            }
        } catch (JsonProcessingException e) {

            throw new AssertionError("Failed to serialise object.", e);
        }
    }
}
