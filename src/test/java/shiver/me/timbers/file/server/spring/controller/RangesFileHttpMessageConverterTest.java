package shiver.me.timbers.file.server.spring.controller;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.server.RangeFile;
import shiver.me.timbers.file.server.Ranges;
import shiver.me.timbers.file.server.RangesFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.Entry;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.server.RangeFiles.buildRanges;
import static shiver.me.timbers.file.server.RangeFiles.buildRangesFile;
import static shiver.me.timbers.file.server.spring.controller.Controllers.buildContent;
import static shiver.me.timbers.file.server.spring.controller.Controllers.mockFile;
import static shiver.me.timbers.file.server.spring.controller.Controllers.mockHttpOutputMessage;
import static shiver.me.timbers.file.server.spring.controller.FileHttpMessageConverterSteps.I_can_check_that_the_message_converter_supports_all_media_types;
import static shiver.me.timbers.file.server.spring.controller.FileHttpMessageConverterSteps.I_can_check_that_the_type_that_the_message_converter_supports;
import static shiver.me.timbers.file.server.spring.controller.FileHttpMessageConverterSteps.I_cannot_read_the_supported_type;
import static shiver.me.timbers.file.server.spring.controller.FileHttpMessageConverterSteps.I_cannot_write_input_to_a_null_message;
import static shiver.me.timbers.file.server.spring.controller.FileHttpMessageConverterSteps.I_cannot_write_input_to_a_null_output_stream;
import static shiver.me.timbers.file.server.spring.controller.FileHttpMessageConverterSteps.I_cannot_write_null_input;

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

        final List<Entry<Integer, Integer>> ranges = Arrays.<Entry<Integer, Integer>>asList(
                new SimpleEntry<>(5, 8), new SimpleEntry<>(0, 4), new SimpleEntry<>(10, 13));

        final RangesFile rangeFiles = buildRangesFile(FILE_ONE, buildRanges(FILE_ONE, ranges));

        final OutputStream output = new ByteArrayOutputStream();

        final HttpHeaders headers = new HttpHeaders();

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);
        when(message.getBody()).thenReturn(output);

        MESSAGE_CONVERTER.write(rangeFiles, null, message);

        final String boundary = extractBoundary(headers);

        final String expectedContent = buildContent(FILE_ONE, TEXT_PLAIN_VALUE, ranges, boundary);

        assertEquals("the files content should be correct.", expectedContent, output.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_write_a_ranges_file_with_invalid_ranges() throws IOException {

        final Ranges ranges = mock(Ranges.class);
        when(ranges.isValid()).thenReturn(false);

        final RangesFile file = mockFile(RangesFile.class);
        when(file.getRanges()).thenReturn(ranges);

        MESSAGE_CONVERTER.write(file, null, mockHttpOutputMessage());
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

    public static String extractBoundary(HttpHeaders headers) {

        return headers.getContentType().getParameter("boundary");
    }
}
