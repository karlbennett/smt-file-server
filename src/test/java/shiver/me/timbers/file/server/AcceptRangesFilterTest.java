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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AcceptRangesFilterTest {

    private static final Filter FILTER = new AcceptRangesFilter();

    private FilterChain chain;

    @Before
    public void setUp() {

        chain = mock(FilterChain.class);
    }

    @Test
    public void I_can_add_the_accept_ranges_header_to_an_http_servlet_response() throws IOException, ServletException {

        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        FILTER.doFilter(request, response, chain);

        verify(response, times(1)).setHeader("Accept-Ranges", "bytes");
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void I_can_continue_filtering_if_the_request_or_response_are_not_for_an_http_request()
            throws IOException, ServletException {

        final ServletRequest request = mock(ServletRequest.class);
        final ServletResponse response = mock(ServletResponse.class);

        FILTER.doFilter(request, response, chain);

        verify(chain, times(1)).doFilter(request, response);
    }
}
