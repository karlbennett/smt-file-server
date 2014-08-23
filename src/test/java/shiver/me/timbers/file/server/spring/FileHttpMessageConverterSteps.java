package shiver.me.timbers.file.server.spring;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static java.lang.String.format;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileHttpMessageConverterSteps {

    public static <T> void I_can_check_that_the_type_that_the_message_converter_supports(
            HttpMessageConverter<T> messageConverter, Class<T> type, Class... unsupportedTypes) {

        assertFalse(format("the %s type should not be supported for read.", type.getSimpleName()),
                messageConverter.canRead(type, null));
        assertTrue(format("the %s type should be supported for write.", type.getSimpleName()),
                messageConverter.canWrite(type, null));

        for (Class unsupportedType : unsupportedTypes) {

            assertFalse(format("the %s type should not be supported for read.", unsupportedType.getSimpleName()),
                    messageConverter.canRead(unsupportedType, null));
            assertFalse(format("the %s type should not be supported for write.", unsupportedType.getSimpleName()),
                    messageConverter.canWrite(unsupportedType, null));
        }
    }

    public static <T> void I_can_check_that_the_message_converter_supports_all_media_types(
            HttpMessageConverter<T> messageConverter) {

        assertThat("all media types should be supported.", messageConverter.getSupportedMediaTypes(),
                contains(MediaType.ALL));
    }

    public static <T> void I_cannot_read_the_supported_type(HttpMessageConverter<T> messageConverter, Class<T> type)
            throws IOException {

        messageConverter.read(type, mock(HttpInputMessage.class));
    }

    public static <T, I extends T> void I_can_write_the_supported_type(HttpMessageConverter<T> messageConverter,
                                                                       String expectedContent, I input)
            throws IOException {

        final OutputStream output = new ByteArrayOutputStream();

        final HttpOutputMessage message = mockHttpOutputMessage(output);

        messageConverter.write(input, null, message);

        assertEquals("the files content should be correct.", expectedContent, output.toString());
    }

    public static <T> void I_cannot_write_null_input(HttpMessageConverter<T> messageConverter) throws IOException {

        messageConverter.write(null, null, mockHttpOutputMessage(new ByteArrayOutputStream()));
    }

    public static <T> void I_cannot_write_input_to_a_null_output_stream(
            HttpMessageConverter<T> messageConverter, T input)
            throws IOException {

        final HttpHeaders headers = mock(HttpHeaders.class);

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);

        messageConverter.write(input, null, message);
    }

    public static <T> void I_cannot_write_input_to_a_null_message(HttpMessageConverter<T> messageConverter, T input)
            throws IOException {

        messageConverter.write(input, null, null);
    }

    private static HttpOutputMessage mockHttpOutputMessage(OutputStream output) throws IOException {

        final HttpHeaders headers = mock(HttpHeaders.class);

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);
        when(message.getBody()).thenReturn(output);
        return message;
    }
}
