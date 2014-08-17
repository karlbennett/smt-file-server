package shiver.me.timbers.file.server.spring;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import shiver.me.timbers.file.io.File;

import java.io.IOException;

import static java.lang.String.format;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.server.ServerConstants.dateFormat;
import static shiver.me.timbers.file.server.spring.Responses.addFileHeaders;

public class ResponsesTest {

    private HttpHeaders headers;

    @Before
    public void setUp() {

        headers = mock(HttpHeaders.class);
    }

    @Test
    public void I_can_create_a_responses_class() {

        new Responses();
    }

    @Test
    public void I_can_add_file_headers_for_a_valid_file() throws IOException {

        addFileHeaders(headers, FILE_ONE);

        verifyHeaders(FILE_ONE, TEXT_PLAIN);
    }

    @Test
    public void I_can_add_file_headers_for_a_valid_json_file() throws IOException {

        addFileHeaders(headers, FILE_FIVE);

        verifyHeaders(FILE_FIVE, APPLICATION_JSON);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_add_file_headers_to_null_headers() throws IOException {

        addFileHeaders(null, FILE_ONE);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_add_file_headers_for_a_null_file() throws IOException {

        addFileHeaders(headers, null);
    }

    private void verifyHeaders(File file, MediaType mediaType) {
        verify(headers, times(1)).setContentType(mediaType);
        verify(headers, times(1)).setETag(format("\"%s_%d_%d\"", file.getName(), file.getSize(),
                file.getModified().getTime()));
        verify(headers, times(1)).set("Last-Modified", dateFormat().format(file.getModified()));
        verify(headers, times(1)).setContentLength(file.getSize());
    }
}
