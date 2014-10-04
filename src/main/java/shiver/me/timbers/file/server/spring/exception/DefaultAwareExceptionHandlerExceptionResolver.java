package shiver.me.timbers.file.server.spring.exception;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.WrappedExceptionHandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This {@link org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver} produces the
 * expected response whenever a default Spring MVC servlet exception is caused by the handling of an exception with the
 * {@link org.springframework.web.bind.annotation.ExceptionHandler} annotation.
 *
 * @author Karl Bennett
 */
public class DefaultAwareExceptionHandlerExceptionResolver extends WrappedExceptionHandlerExceptionResolver {

    private final HandlerExceptionResolver defaultHandlerExceptionResolver;

    public DefaultAwareExceptionHandlerExceptionResolver(
            HandlerExceptionResolver defaultHandlerExceptionResolver,
            ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver) {
        super(exceptionHandlerExceptionResolver);

        this.defaultHandlerExceptionResolver = defaultHandlerExceptionResolver;
    }

    @Override
    public ModelAndView doResolveHandlerMethodException(HttpServletRequest request,
                                                        HttpServletResponse response, HandlerMethod handlerMethod,
                                                        Exception exception) {

        final ServletInvocableHandlerMethod exceptionHandlerMethod =
                getExceptionHandlerMethod(handlerMethod, exception);

        if (exceptionHandlerMethod == null) {
            return null;
        }

        exceptionHandlerMethod.setHandlerMethodArgumentResolvers(this.getArgumentResolvers());
        exceptionHandlerMethod.setHandlerMethodReturnValueHandlers(this.getReturnValueHandlers());

        final ServletWebRequest webRequest = new ServletWebRequest(request, response);
        final ModelAndViewContainer mavContainer = new ModelAndViewContainer();

        try {

            logger.debug("Invoking @ExceptionHandler method: " + exceptionHandlerMethod);

            exceptionHandlerMethod.invokeAndHandle(webRequest, mavContainer, exception);

        } catch (ServletException servletException) {

            logger.warn("Failed to invoke @ExceptionHandler method: " + exceptionHandlerMethod, servletException);

            return defaultHandlerExceptionResolver.resolveException(request, response, handlerMethod, servletException);

        } catch (Exception invocationEx) {

            logger.error("Failed to invoke @ExceptionHandler method: " + exceptionHandlerMethod, invocationEx);

            return null;
        }

        if (mavContainer.isRequestHandled()) {

            return new ModelAndView();

        } else {

            ModelAndView mav = new ModelAndView().addAllObjects(mavContainer.getModel());
            mav.setViewName(mavContainer.getViewName());

            if (!mavContainer.isViewReference()) {
                mav.setView((View) mavContainer.getView());
            }

            return mav;
        }
    }
}
