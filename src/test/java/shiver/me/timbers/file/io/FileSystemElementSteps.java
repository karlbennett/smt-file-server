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

public class FileSystemElementSteps {

    public static void The_file_system_elements_name_should_be_correct(String name, FileSystemElement element) {

        assertEquals("the name of the file system element should be correct.", name, element.getName());
    }

    public static void The_file_system_elements_extension_should_be_correct(String extension,
                                                                            FileSystemElement element) {

        assertEquals("the extension of the file system element should be correct.", extension, element.getExtension());
    }

    public static void The_file_system_elements_modification_date_should_be_correct(Date modified,
                                                                                    FileSystemElement element) {

        assertEquals("the modification date of the file system element should be correct.", modified,
                element.getModified());
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

    public static void The_file_system_element_should_have_the_correct_to_string_value(String string,
                                                                                       FileSystemElement element) {

        assertEquals("the file system elements toString value should be correct.", string, element.toString());
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
