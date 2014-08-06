package shiver.me.timbers.file.server;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import shiver.me.timbers.file.io.InvalidPathException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;

public class FileHttpMessageConverterTest {

    private static final HttpMessageConverter<File> MESSAGE_CONVERTER = new FileHttpMessageConverter();

    @Test
    public void I_can_check_that_the_file_message_converter_supports_files_only() {

        assertTrue("the File type should be supported for read.", MESSAGE_CONVERTER.canRead(File.class, null));
        assertTrue("the File type should be supported for write.", MESSAGE_CONVERTER.canWrite(File.class, null));

        assertFalse("the String type should not be supported for read.", MESSAGE_CONVERTER.canRead(String.class, null));
        assertFalse("the String type should not be supported for write.",
                MESSAGE_CONVERTER.canWrite(String.class, null));

        assertFalse("the Object type should not be supported for read.", MESSAGE_CONVERTER.canRead(Object.class, null));
        assertFalse("the Object type should not be supported for write.",
                MESSAGE_CONVERTER.canWrite(Object.class, null));
    }

    @Test
    public void I_can_read_a_file() throws IOException {

        assertFileReadForPath("/some/path/to/a/file.txt");
    }

    @Test
    public void I_can_read_a_file_with_an_empty_path() throws IOException {

        assertFileReadForPath("");
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_read_a_file_with_a_null_path() throws IOException {

        final HttpInputMessage message = mock(HttpInputMessage.class);

        MESSAGE_CONVERTER.read(null, message);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_read_a_file_with_a_null_message() throws IOException {

        MESSAGE_CONVERTER.read(null, null);
    }

    @Test
    public void I_can_write_a_file() throws IOException {

        final OutputStream output = new ByteArrayOutputStream();

        final HttpOutputMessage message = mockHttpOutputMessage(output);

        MESSAGE_CONVERTER.write(new File(FILE_ONE.getAbsolutePath()), null, message);

        assertEquals("the files content should be correct.", FILE_ONE.getContent(), output.toString());
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_write_an_invalid_file() throws IOException {

        MESSAGE_CONVERTER.write(new File("invalid"), null, mockHttpOutputMessage(new ByteArrayOutputStream()));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_null_file() throws IOException {

        MESSAGE_CONVERTER.write(null, null, mockHttpOutputMessage(new ByteArrayOutputStream()));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_output_stream() throws IOException {

        final HttpHeaders headers = mock(HttpHeaders.class);

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);

        MESSAGE_CONVERTER.write(new File(FILE_ONE.getAbsolutePath()), null, message);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_message() throws IOException {

        MESSAGE_CONVERTER.write(new File(FILE_ONE.getAbsolutePath()), null, null);
    }

    private static void assertFileReadForPath(String path) throws IOException {
        final InputStream input = new ByteArrayInputStream(path.getBytes());

        final HttpInputMessage message = mock(HttpInputMessage.class);
        when(message.getBody()).thenReturn(input);

        final File file = MESSAGE_CONVERTER.read(null, message);

        assertEquals("the files path should be correct.", path, file.getPath());
    }

    private static HttpOutputMessage mockHttpOutputMessage(OutputStream output) throws IOException {

        final HttpHeaders headers = mock(HttpHeaders.class);

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);
        when(message.getBody()).thenReturn(output);
        return message;
    }
}
