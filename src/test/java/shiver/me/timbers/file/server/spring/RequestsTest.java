package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import shiver.me.timbers.file.io.Directory;
import shiver.me.timbers.file.io.StreamFile;
import shiver.me.timbers.file.server.Creator;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.server.spring.Requests.getAttributeFrom;
import static shiver.me.timbers.file.server.spring.Requests.getDirectoryFrom;
import static shiver.me.timbers.file.server.spring.Requests.getFileFrom;

public class RequestsTest {

    @Test
    public void I_can_get_a_directory_a_request() {

        final Directory directory = mock(Directory.class);

        final HttpServletRequest request = mockRequest("directory", directory);

        assertEquals("the correct value should be returned.", directory, getDirectoryFrom(request));
    }

    @Test(expected = NoDirectoryException.class)
    public void I_get_the_right_exception_when_there_is_no_directory() {

        getDirectoryFrom(mock(HttpServletRequest.class));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_get_a_directory_from_a_null_request() {

        getDirectoryFrom(null);
    }

    @Test
    public void I_can_get_a_file_a_request() {

        final StreamFile file = mock(StreamFile.class);

        final HttpServletRequest request = mockRequest("file", file);

        assertEquals("the correct value should be returned.", file, getFileFrom(request));
    }

    @Test(expected = NoFileException.class)
    public void I_get_the_right_exception_when_there_is_no_file() {

        getFileFrom(mock(HttpServletRequest.class));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_get_a_file_from_a_null_request() {

        getFileFrom(null);
    }

    @Test
    public void I_can_get_an_attribute_from_a_request() {

        final String name = "attribute";
        final Integer value = 123;

        final HttpServletRequest request = mockRequest(name, value);

        assertEquals("the correct value should be returned.", value, getAttributeFrom(name, request, null));
    }

    @Test(expected = TestException.class)
    public void I_cannot_get_an_attribute_from_an_empty_request() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        getAttributeFrom("attribute", request, new Creator<RuntimeException>() {
            @Override
            public RuntimeException create() {
                return new TestException();
            }
        });
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_get_an_attribute_from_a_null_request() {

        getAttributeFrom("attribute", null, null);
    }

    @Test
    public void I_can_create_a_request_class() {

        new Requests();
    }

    private static HttpServletRequest mockRequest(String name, Object value) {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute(name)).thenReturn(value);

        return request;
    }

    private static class TestException extends RuntimeException {
    }
}
