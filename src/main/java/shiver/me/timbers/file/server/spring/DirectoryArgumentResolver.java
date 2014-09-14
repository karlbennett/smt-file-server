package shiver.me.timbers.file.server.spring;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shiver.me.timbers.file.io.Directory;

import javax.servlet.http.HttpServletRequest;

import static shiver.me.timbers.file.server.spring.Requests.getDirectoryFrom;

/**
 * This class will move the {@link Directory} out of an {@link HttpServletRequest} attribute into an argument.
 *
 * @author Karl Bennett
 */
public class DirectoryArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Directory.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {

        return getDirectoryFrom(request.getNativeRequest(HttpServletRequest.class));
    }
}
