package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import org.springframework.http.converter.HttpMessageConverter;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.server.Range;
import shiver.me.timbers.file.server.RangeFile;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.server.RangeFiles.buildRangeFile;
import static shiver.me.timbers.file.server.spring.Controllers.mockFile;
import static shiver.me.timbers.file.server.spring.Controllers.mockHttpOutputMessage;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_can_check_that_the_message_converter_supports_all_media_types;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_can_check_that_the_type_that_the_message_converter_supports;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_can_write_the_supported_type;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_cannot_read_the_supported_type;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_cannot_write_input_to_a_null_message;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_cannot_write_input_to_a_null_output_stream;
import static shiver.me.timbers.file.server.spring.FileHttpMessageConverterSteps.I_cannot_write_null_input;

public class RangeFileHttpMessageConverterTest {

    private static final HttpMessageConverter<RangeFile> MESSAGE_CONVERTER = new RangeFileHttpMessageConverter();

    @Test
    public void I_can_check_that_the_file_message_converter_supports_range_files_only() {

        I_can_check_that_the_type_that_the_message_converter_supports(MESSAGE_CONVERTER, RangeFile.class,
                File.class, java.io.File.class, String.class, Object.class);
    }

    @Test
    public void I_can_check_that_the_file_message_converter_supports_all_media_types() {

        I_can_check_that_the_message_converter_supports_all_media_types(MESSAGE_CONVERTER);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void I_cannot_read_a_file() throws IOException {

        I_cannot_read_the_supported_type(MESSAGE_CONVERTER, RangeFile.class);
    }

    @Test
    public void I_can_write_a_file() throws IOException {

        I_can_write_the_supported_type(MESSAGE_CONVERTER, FILE_ONE.getContent(), buildRangeFile(FILE_ONE));
    }

    @Test
    public void I_can_write_a_partial_file() throws IOException {

        I_can_write_the_supported_type(MESSAGE_CONVERTER, FILE_ONE.getContent().substring(6, 11),
                buildRangeFile(FILE_ONE, 6, 10));
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_write_a_range_file_with_an_invalid_range() throws IOException {

        final Range range = mock(Range.class);
        when(range.isValid()).thenReturn(false);

        final RangeFile file = mockFile(RangeFile.class);
        when(file.getRange()).thenReturn(range);

        MESSAGE_CONVERTER.write(file, null, mockHttpOutputMessage());
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_null_file() throws IOException {

        I_cannot_write_null_input(MESSAGE_CONVERTER);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_output_stream() throws IOException {

        I_cannot_write_input_to_a_null_output_stream(MESSAGE_CONVERTER, buildRangeFile(FILE_ONE));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_message() throws IOException {

        I_cannot_write_input_to_a_null_message(MESSAGE_CONVERTER, buildRangeFile(FILE_ONE));
    }
}
