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
                creator.create(FILE_ONE.getAbsolutePath()));
    }

    public static void The_file_system_element_should_have_correct_equality(FileSystemElement left,
                                                                            FileSystemElement right) {

        assertEquals("the file system element should be equal to it's self.", left, left);

        assertEquals("the file system elements should be equal.", left, right);

        assertEquals("the file system element hash codes should be equal.", left.hashCode(), right.hashCode());

        assertNotEquals("the file system element should not be equal to an element with a different name.", left,
                mockFileSystemElement("different", left.getModified()));

        assertNotEquals("the file system element should not be equal to an element with a different modified date.",
                left, mockFileSystemElement(left.getName(), new Date()));

        assertNotEquals("the file system element should not be equal to object.", left, new Object());

        assertNotEquals("the file system element should not be equal to null.", left, null);
    }

    private static FileSystemElement mockFileSystemElement(String name, Date modified) {

        final FileSystemElement mock = mock(FileSystemElement.class);
        when(mock.getName()).thenReturn(name);
        when(mock.getModified()).thenReturn(modified);

        return mock;
    }

    public static void The_file_system_element_should_have_the_correct_to_string_value(FileSystemElement expected,
                                                                                       FileSystemElement actual) {

        assertEquals("the file system elements toString value should be correct.", expected.getName(),
                actual.toString());
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
