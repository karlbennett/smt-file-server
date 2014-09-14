package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import org.springframework.web.context.request.NativeWebRequest;
import shiver.me.timbers.file.io.Directory;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.StreamFile;
import shiver.me.timbers.file.server.Ranges;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RangesArgumentResolverTest {

    private static final RangesArgumentResolver RESOLVER = new RangesArgumentResolver();

    @Test
    @SuppressWarnings("unchecked")
    public void I_can_check_the_supported_argument_type() {

        ArgumentResolverSteps.I_can_check_the_supported_argument_type(RESOLVER, Ranges.class, Directory.class,
                File.class, String.class, Integer.class, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_check_a_null_argument_type() {

        ArgumentResolverSteps.I_cannot_check_a_null_argument_type(RESOLVER);
    }

    @Test
    public void I_can_resolve_a_directory_from_the_request() throws Exception {

        final long fileSize = 100L;

        final StreamFile file = mock(StreamFile.class);
        when(file.getSize()).thenReturn(fileSize);

        final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getAttribute("file")).thenReturn(file);

        final String range = "bytes=0-50";

        final NativeWebRequest request = mock(NativeWebRequest.class);
        when(request.getNativeRequest(HttpServletRequest.class)).thenReturn(servletRequest);
        when(request.getHeader("Range")).thenReturn(range);

        assertThat("the directory should be resolved.", (Ranges) RESOLVER.resolveArgument(null, null, request, null),
                equalTo(new Ranges(range, fileSize)));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_resolve_a_directory_from_a_null_request() throws Exception {

        ArgumentResolverSteps.I_cannot_resolve_a_directory_from_a_null_request(RESOLVER);
    }
}
