package shiver.me.timbers.file.server;

import org.junit.Test;
import shiver.me.timbers.file.io.InvalidPathException;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.server.Requests.getAbsolutePathAttribute;

public class RequestsTest {

    @Test
    public void I_can_create_a_request_class() {

        new Requests();
    }

    @Test
    public void I_can_get_the_absolute_path_attribute() {

        final String absolutePath = "/some/absolute/path";
        final HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("absolutePath")).thenReturn(absolutePath);

        assertEquals("the absolute path should be correct.", absolutePath, getAbsolutePathAttribute(request));
    }

    @Test(expected = InvalidPathException.class)
    public void I_cannot_get_the_absolute_path_attribute_when_it_is_null() {

        final HttpServletRequest request = mock(HttpServletRequest.class);

        getAbsolutePathAttribute(request);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_get_the_absolute_path_attribute_when_the_request_is_null() {

        getAbsolutePathAttribute(null);
    }
}
