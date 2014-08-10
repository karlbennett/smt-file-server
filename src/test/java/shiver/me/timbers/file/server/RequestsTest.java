package shiver.me.timbers.file.server;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import shiver.me.timbers.file.io.File;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.server.Requests.addFileHeaders;
import static shiver.me.timbers.file.server.Requests.getAttribute;
import static shiver.me.timbers.file.server.ServerConstants.dateFormat;

public class RequestsTest {

    private HttpHeaders headers;

    @Before
    public void setUp() {

        headers = mock(HttpHeaders.class);
    }

    @Test
    public void I_can_get_an_attribute_from_a_request() {

        final String name = "attribute";
        final Integer value = 123;

        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute(name)).thenReturn(value);

        assertEquals("the correct value should be returned.", value, getAttribute(name, request, null));
    }

    @Test(expected = TestException.class)
    public void I_cannot_get_an_attribute_from_an_empty_request() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        getAttribute("attribute", request, new Creator<RuntimeException>() {
            @Override
            public RuntimeException create() {
                return new TestException();
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_get_an_attribute_from_a_null_request() {

        getAttribute("attribute", null, null);
    }

    @Test
    public void I_can_create_a_request_class() {

        new Requests();
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

    private static class TestException extends RuntimeException {
    }
}
