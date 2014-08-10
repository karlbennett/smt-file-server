package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import shiver.me.timbers.file.server.RangeFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.server.RangeFiles.buildRangeFile;

public class RangeFileHttpMessageConverterTest {

    private static final HttpMessageConverter<RangeFile> MESSAGE_CONVERTER = new RangeFileHttpMessageConverter();

    @Test
    public void I_can_check_that_the_file_message_converter_supports_files_only() {

        assertFalse("the RangeFile type should not be supported for read.",
                MESSAGE_CONVERTER.canRead(RangeFile.class, null));
        assertTrue("the RangeFile type should be supported for write.",
                MESSAGE_CONVERTER.canWrite(RangeFile.class, null));

        assertFalse("the String type should not be supported for read.", MESSAGE_CONVERTER.canRead(String.class, null));
        assertFalse("the String type should not be supported for write.",
                MESSAGE_CONVERTER.canWrite(String.class, null));

        assertFalse("the Object type should not be supported for read.", MESSAGE_CONVERTER.canRead(Object.class, null));
        assertFalse("the Object type should not be supported for write.",
                MESSAGE_CONVERTER.canWrite(Object.class, null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void I_cannot_read_a_file() throws IOException {

        MESSAGE_CONVERTER.read(RangeFile.class, mock(HttpInputMessage.class));
    }

    @Test
    public void I_can_write_a_file() throws IOException {

        final OutputStream output = new ByteArrayOutputStream();

        final HttpOutputMessage message = mockHttpOutputMessage(output);

        MESSAGE_CONVERTER.write(buildRangeFile(FILE_ONE), null, message);

        assertEquals("the files content should be correct.", FILE_ONE.getContent(), output.toString());
    }

    @Test
    public void I_can_write_a_partial_file() throws IOException {

        final OutputStream output = new ByteArrayOutputStream();

        final HttpOutputMessage message = mockHttpOutputMessage(output);

        MESSAGE_CONVERTER.write(buildRangeFile(FILE_ONE, 6, 10), null, message);

        // Range end is inclusive while String.substring end is exclusive.
        assertEquals("the files content should be correct.", FILE_ONE.getContent().substring(6, 11), output.toString());
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

        MESSAGE_CONVERTER.write(buildRangeFile(FILE_ONE), null, message);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_message() throws IOException {

        MESSAGE_CONVERTER.write(buildRangeFile(FILE_ONE), null, null);
    }

    private static HttpOutputMessage mockHttpOutputMessage(OutputStream output) throws IOException {

        final HttpHeaders headers = mock(HttpHeaders.class);

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);
        when(message.getBody()).thenReturn(output);
        return message;
    }
}
