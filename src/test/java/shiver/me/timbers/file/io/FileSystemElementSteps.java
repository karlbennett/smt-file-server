package shiver.me.timbers.file.io;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Date;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static shiver.me.timbers.Constants.OBJECT_MAPPER;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;

public class FileSystemElementSteps {

    public static void The_file_system_elements_name_should_be_correct(FileSystemElement expected,
                                                                       FileSystemElement actual) {

        assertEquals("the name of the file system element should be correct.", expected.getName(), actual.getName());
    }

    public static void The_file_system_elements_modification_date_should_be_correct(FileSystemElement expected,
                                                                                    FileSystemElement actual) {

        assertEquals("the modification date of the file system element should be correct.", expected.getModified(),
                actual.getModified());
    }

    public static void The_file_system_element_should_have_correct_equality(FileSystemElementCreator creator) {

        The_file_system_element_should_have_correct_equality(creator.create(FILE_ONE.getAbsolutePath()),
                creator.create(FILE_ONE.getAbsolutePath()), creator);
    }

    public static void The_file_system_element_should_have_correct_equality(FileSystemElement left,
                                                                            FileSystemElement right,
                                                                            FileSystemElementCreator creator) {

        assertEquals("the file system element should be equal to it's self.", left, left);

        assertEquals("the file system elements should be equal.", left, right);

        assertEquals("the file system element hash codes should be equal.", left.hashCode(), right.hashCode());

        assertNotEquals("the file system element should not be equal to an element with a different name.", left,
                creator.mock("different", left.getModified()));

        assertNotEquals("the file system element should not be equal to an element with a different modified date.",
                left, creator.mock(left.getName(), new Date()));

        assertNotEquals("the file system element should not be equal to object.", left, new Object());

        assertNotEquals("the file system element should not be equal to null.", left, null);
    }

    public static void The_file_system_element_should_have_the_correct_to_string_value(TestFileSystemElement expected,
                                                                                       FileSystemElement actual) {

        assertEquals("the file system elements toString value should be correct.", expected.toString(),
                actual.toString());
    }

    public static String The_file_system_element_should_be_able_to_be_serialised(FileSystemElement element,
                                                                                 String... values) {

        try {

            final String serialisedFileSystemElement = OBJECT_MAPPER.writeValueAsString(element);

            for (String value : values) {

                assertThat(format("the file system element %s could not be serialised.", element),
                        serialisedFileSystemElement, containsString(value));
            }

            return serialisedFileSystemElement;

        } catch (JsonProcessingException e) {

            throw new AssertionError("Failed to serialise object.", e);
        }
    }
}
