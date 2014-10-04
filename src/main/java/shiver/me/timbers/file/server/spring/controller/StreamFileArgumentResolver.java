package shiver.me.timbers.file.server.spring.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shiver.me.timbers.file.io.StreamFile;

import javax.servlet.http.HttpServletRequest;

import static shiver.me.timbers.file.server.spring.controller.Requests.getFileFrom;

/**
 * This class will move the {@link StreamFile} out of an {@link HttpServletRequest} attribute into an argument.
 *
 * @author Karl Bennett
 */
public class StreamFileArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return StreamFile.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {

        return getFileFrom(request.getNativeRequest(HttpServletRequest.class));
    }
}
