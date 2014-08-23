package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.TestFile;
import shiver.me.timbers.file.server.Range;
import shiver.me.timbers.file.server.RangeFile;
import shiver.me.timbers.file.server.Ranges;
import shiver.me.timbers.file.server.RangesFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.server.RangeFiles.buildRanges;
import static shiver.me.timbers.file.server.RangeFiles.buildRangesFile;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_can_check_that_the_message_converter_supports_all_media_types;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_can_check_that_the_type_that_the_message_converter_supports;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_cannot_read_the_supported_type;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_cannot_write_input_to_a_null_message;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_cannot_write_input_to_a_null_output_stream;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_cannot_write_null_input;

public class RangesFileHttpMessageConverterTest {

    private static final HttpMessageConverter<RangesFile> MESSAGE_CONVERTER = new RangesFileHttpMessageConverter();

    @Test
    public void I_can_check_that_the_file_message_converter_supports_files_only() {

        I_can_check_that_the_type_that_the_message_converter_supports(MESSAGE_CONVERTER, RangesFile.class,
                RangeFile.class, File.class, java.io.File.class, String.class, Object.class);
    }

    @Test
    public void I_can_check_that_the_file_message_converter_supports_all_media_types() {

        I_can_check_that_the_message_converter_supports_all_media_types(MESSAGE_CONVERTER);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void I_cannot_read_a_file() throws IOException {

        I_cannot_read_the_supported_type(MESSAGE_CONVERTER, RangesFile.class);
    }

    @Test
    public void I_can_write_a_file() throws IOException {

        final long fileSize = FILE_ONE.getSize();

        final RangesFile rangeFiles = buildRangesFile(FILE_ONE,
                buildRanges(FILE_ONE,
                        new Range(5, 8, fileSize), new Range(0, 4, fileSize), new Range(10, 13, fileSize)));

        final OutputStream output = new ByteArrayOutputStream();

        final HttpHeaders headers = new HttpHeaders();

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);
        when(message.getBody()).thenReturn(output);

        MESSAGE_CONVERTER.write(rangeFiles, null, message);

        final String boundary = extractBoundary(headers);

        final String expectedContent = buildContent(FILE_ONE, TEXT_PLAIN_VALUE, rangeFiles.getRanges(), boundary);

        assertEquals("the files content should be correct.", expectedContent, output.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_write_a_ranges_file_with_invalid_ranges() throws IOException {

        final Ranges ranges = mock(Ranges.class);
        when(ranges.isValid()).thenReturn(false);

        final RangesFile file = mock(RangesFile.class);
        when(file.getRanges()).thenReturn(ranges);

        MESSAGE_CONVERTER.write(file, null, mock(HttpOutputMessage.class));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_null_file() throws IOException {

        I_cannot_write_null_input(MESSAGE_CONVERTER);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_output_stream() throws IOException {

        I_cannot_write_input_to_a_null_output_stream(MESSAGE_CONVERTER, buildRangesFile(FILE_ONE));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_message() throws IOException {

        I_cannot_write_input_to_a_null_message(MESSAGE_CONVERTER, buildRangesFile(FILE_ONE));
    }

    private static String extractBoundary(HttpHeaders headers) {

        return headers.getContentType().getParameter("boundary");
    }

    private static String buildContent(TestFile<String> file, String mediaType, Ranges ranges, String boundary) {

        final StringBuilder content = new StringBuilder();

        for (Range range : ranges) {

            content.append("\n");
            content.append("--").append(boundary).append("\n");
            content.append("Content-Type: ").append(mediaType).append("\n");
            content.append("Content-Range: bytes ").append(range).append("/").append(file.getSize()).append("\n");
            content.append("\n");
            content.append(file.getContent().substring((int) range.getStart(), (int) range.getEnd() + 1));
        }

        content.append("\n");
        content.append("--").append(boundary).append("--").append("\n");

        return content.toString();
    }
}
