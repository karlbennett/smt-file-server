package shiver.me.timbers.file.server;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

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
import static shiver.me.timbers.file.server.Requests.getAbsoluteFile;
import static shiver.me.timbers.file.server.ServerConstants.dateFormat;

public class RequestsTest {

    private HttpHeaders headers;

    @Before
    public void setUp() {

        headers = mock(HttpHeaders.class);
    }

    @Test
    public void I_can_create_a_request_class() {

        new Requests();
    }

    @Test
    public void I_can_get_the_absolute_path_attribute() {

        final File file = mock(File.class);

        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("file")).thenReturn(file);

        assertEquals("the absolute path should be correct.", file, getAbsoluteFile(request));
    }

    @Test(expected = NoFileException.class)
    public void I_cannot_get_the_absolute_path_attribute_when_it_is_null() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        getAbsoluteFile(request);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_get_the_absolute_path_attribute_when_the_request_is_null() {

        getAbsoluteFile(null);
    }

    @Test
    public void I_can_add_file_headers_for_a_valid_file() throws IOException {

        final File file = new File(FILE_ONE.getAbsolutePath());

        addFileHeaders(headers, file);

        verifyHeaders(file, TEXT_PLAIN);
    }

    @Test
    public void I_can_add_file_headers_for_a_valid_json_file() throws IOException {

        final File file = new File(FILE_FIVE.getAbsolutePath());

        addFileHeaders(headers, file);

        verifyHeaders(file, APPLICATION_JSON);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_add_file_headers_to_null_headers() throws IOException {

        addFileHeaders(null, new File(FILE_ONE.getAbsolutePath()));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_add_file_headers_for_a_null_file() throws IOException {

        addFileHeaders(headers, null);
    }

    private void verifyHeaders(File file, MediaType mediaType) {
        verify(headers, times(1)).setContentType(mediaType);
        verify(headers, times(1)).setETag(format("\"%s_%d_%d\"", file.getName(), file.length(), file.lastModified()));
        verify(headers, times(1)).set("Last-Modified", dateFormat().format(new Date(file.lastModified())));
        verify(headers, times(1)).setContentLength(file.length());
    }
}
