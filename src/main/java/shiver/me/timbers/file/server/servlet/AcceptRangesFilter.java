package shiver.me.timbers.file.server.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter that adds the Accept-Ranges header to all HTTP responses.
 *
 * @author Karl Bennett
 */
@WebFilter(urlPatterns = "/*")
public class AcceptRangesFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        response.setHeader("Accept-Ranges", "bytes");

        chain.doFilter(request, response);
    }
}
