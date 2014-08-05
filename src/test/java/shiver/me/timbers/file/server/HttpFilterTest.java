package shiver.me.timbers.file.server;

import org.junit.Before;
import org.junit.Test;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class HttpFilterTest {

    private HttpServletRequest httpRequest;
    private HttpServletResponse httpResponse;
    private ServletRequest servletRequest;
    private ServletResponse servletResponse;
    private FilterChain chain;

    private Filter assertionErrorFilter;

    @Before
    public void setUp() {

        httpRequest = mock(HttpServletRequest.class);
        httpResponse = mock(HttpServletResponse.class);
        servletRequest = mock(ServletRequest.class);
        servletResponse = mock(ServletResponse.class);
        chain = mock(FilterChain.class);

        assertionErrorFilter = new AssertionErrorHttpFilter();
    }

    @Test
    public void I_can_call_the_filter_init_an_destroy_methods() throws ServletException {

        assertionErrorFilter.init(null);
        assertionErrorFilter.destroy();
    }

    @Test
    public void I_can_filter_an_http_request_and_response() throws IOException, ServletException {

        final boolean[] ran = {false};

        final Filter filter = new HttpFilter() {
            @Override
            public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                ran[0] = true;

                chain.doFilter(request, response);
            }
        };

        filter.doFilter(httpRequest, httpResponse, chain);

        assertTrue("the doFilter method should have ran.", ran[0]);

        verify(chain, times(1)).doFilter(httpRequest, httpResponse);
    }

    @Test
    public void I_cannot_filter_an_http_request_and_servlet_response() throws IOException, ServletException {

        assertionErrorFilter.doFilter(httpRequest, servletResponse, chain);

        verify(chain, times(1)).doFilter(httpRequest, servletResponse);
    }

    @Test
    public void I_cannot_filter_a_servlet_request_and_http_response() throws IOException, ServletException {

        assertionErrorFilter.doFilter(servletRequest, httpResponse, chain);

        verify(chain, times(1)).doFilter(servletRequest, httpResponse);
    }

    @Test
    public void I_cannot_filter_a_servlet_request_and_response() throws IOException, ServletException {

        assertionErrorFilter.doFilter(servletRequest, servletResponse, chain);

        verify(chain, times(1)).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void I_cannot_filter_a_null_request_and_http_response() throws IOException, ServletException {

        assertionErrorFilter.doFilter(null, httpResponse, chain);

        verify(chain, times(1)).doFilter(isNull(ServletRequest.class), eq(httpResponse));
    }

    @Test
    public void I_cannot_filter_an_http_request_and_null_response() throws IOException, ServletException {

        assertionErrorFilter.doFilter(httpRequest, null, chain);

        verify(chain, times(1)).doFilter(eq(httpRequest), isNull(ServletResponse.class));
    }

    @Test
    public void I_cannot_filter_a_null_request_and_response() throws IOException, ServletException {

        assertionErrorFilter.doFilter(null, null, chain);

        verify(chain, times(1)).doFilter(isNull(ServletRequest.class), isNull(ServletResponse.class));
    }

    private static class AssertionErrorHttpFilter extends HttpFilter {

        @Override
        public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            throw new AssertionError("doFilter this should not be called.");
        }
    }
}
