package shiver.me.timbers.file.server.spring.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;

import static java.lang.String.format;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ArgumentResolverSteps {

    @SuppressWarnings("unchecked")
    public static void I_can_check_the_supported_argument_type(HandlerMethodArgumentResolver resolver, Class success,
                                                               Class... failures) {

        final MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.getParameterType()).thenReturn(success, failures);

        assertThat(format("the %s type should be resolvable.", success.getName()),
                resolver.supportsParameter(parameter), equalTo(true));

        for (Class failure : failures) {

            assertThat(format("the %s type should not be resolvable.", failure.getName()),
                    resolver.supportsParameter(parameter), equalTo(false));
        }
    }

    public static void I_cannot_check_a_null_argument_type(HandlerMethodArgumentResolver resolver) {

        final MethodParameter parameter = mock(MethodParameter.class);
        when(parameter.getParameterType()).thenReturn(null);

        resolver.supportsParameter(parameter);
    }

    @SuppressWarnings("unchecked")
    public static <T> void I_can_resolve_a_directory_from_the_request(HandlerMethodArgumentResolver resolver, String name,
                                                                      T value) throws Exception {

        final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getAttribute(name)).thenReturn(value);

        final NativeWebRequest request = mock(NativeWebRequest.class);
        when(request.getNativeRequest(HttpServletRequest.class)).thenReturn(servletRequest);

        assertThat("the directory should be resolved.", (T) resolver.resolveArgument(null, null, request, null),
                equalTo(value));
    }

    public static void I_cannot_resolve_a_directory_from_a_null_request(HandlerMethodArgumentResolver resolver)
            throws Exception {

        resolver.resolveArgument(null, null, null, null);
    }
}
