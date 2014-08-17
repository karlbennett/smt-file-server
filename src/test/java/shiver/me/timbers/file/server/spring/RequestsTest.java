package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import shiver.me.timbers.file.server.Creator;

import javax.servlet.http.HttpServletRequest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.server.spring.Requests.getAttribute;

public class RequestsTest {

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

    private static class TestException extends RuntimeException {
    }
}
