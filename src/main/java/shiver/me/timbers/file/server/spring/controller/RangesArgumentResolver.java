package shiver.me.timbers.file.server.spring.controller;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.server.Ranges;

import javax.servlet.http.HttpServletRequest;

import static shiver.me.timbers.file.server.spring.controller.Requests.RANGE;
import static shiver.me.timbers.file.server.spring.controller.Requests.getFileFrom;

/**
 * This class will move the {@link shiver.me.timbers.file.io.StreamFile} out of an {@link javax.servlet.http.HttpServletRequest} attribute into an argument.
 *
 * @author Karl Bennett
 */
public class RangesArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Ranges.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  final NativeWebRequest request, WebDataBinderFactory binderFactory) throws Exception {

        final File file = getFileFrom(request.getNativeRequest(HttpServletRequest.class));

        final String range = request.getHeader(RANGE);

        return new Ranges(range, file.getSize());
    }
}
