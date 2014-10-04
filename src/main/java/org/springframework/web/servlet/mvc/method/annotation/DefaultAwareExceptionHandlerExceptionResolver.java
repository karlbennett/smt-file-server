package org.springframework.web.servlet.mvc.method.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * This {@link ExceptionHandlerExceptionResolver} produces the expected response whenever a default Spring MVC servlet
 * exception is caused by the handling of an exception with the
 * {@link org.springframework.web.bind.annotation.ExceptionHandler} annotation.
 *
 * @author Karl Bennett
 */
public class DefaultAwareExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

    private final HandlerExceptionResolver defaultHandlerExceptionResolver;
    private final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver;

    public DefaultAwareExceptionHandlerExceptionResolver(
            HandlerExceptionResolver defaultHandlerExceptionResolver,
            ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver) {

        this.defaultHandlerExceptionResolver = defaultHandlerExceptionResolver;
        this.exceptionHandlerExceptionResolver = exceptionHandlerExceptionResolver;
    }

    @Override
    protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request,
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

    @Override
    public ApplicationContext getApplicationContext() {
        return exceptionHandlerExceptionResolver.getApplicationContext();
    }

    @Override
    public HandlerMethodArgumentResolverComposite getArgumentResolvers() {
        return exceptionHandlerExceptionResolver.getArgumentResolvers();
    }

    @Override
    public ContentNegotiationManager getContentNegotiationManager() {
        return exceptionHandlerExceptionResolver.getContentNegotiationManager();
    }

    @Override
    public List<HandlerMethodArgumentResolver> getCustomArgumentResolvers() {
        return exceptionHandlerExceptionResolver.getCustomArgumentResolvers();
    }

    @Override
    public List<HandlerMethodReturnValueHandler> getCustomReturnValueHandlers() {
        return exceptionHandlerExceptionResolver.getCustomReturnValueHandlers();
    }

    @Override
    public List<HandlerMethodArgumentResolver> getDefaultArgumentResolvers() {
        return exceptionHandlerExceptionResolver.getDefaultArgumentResolvers();
    }

    @Override
    public List<HandlerMethodReturnValueHandler> getDefaultReturnValueHandlers() {
        return exceptionHandlerExceptionResolver.getDefaultReturnValueHandlers();
    }

    @Override
    public Map<ControllerAdviceBean, ExceptionHandlerMethodResolver> getExceptionHandlerAdviceCache() {
        return exceptionHandlerExceptionResolver.getExceptionHandlerAdviceCache();
    }

    @Override
    public ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
        return exceptionHandlerExceptionResolver.getExceptionHandlerMethod(handlerMethod, exception);
    }

    @Override
    public List<HttpMessageConverter<?>> getMessageConverters() {
        return exceptionHandlerExceptionResolver.getMessageConverters();
    }

    @Override
    public HandlerMethodReturnValueHandlerComposite getReturnValueHandlers() {
        return exceptionHandlerExceptionResolver.getReturnValueHandlers();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        exceptionHandlerExceptionResolver.setApplicationContext(applicationContext);
    }

    @Override
    public void setArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        exceptionHandlerExceptionResolver.setArgumentResolvers(argumentResolvers);
    }

    @Override
    public void setContentNegotiationManager(ContentNegotiationManager contentNegotiationManager) {
        exceptionHandlerExceptionResolver.setContentNegotiationManager(contentNegotiationManager);
    }

    @Override
    public void setCustomArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        exceptionHandlerExceptionResolver.setCustomArgumentResolvers(argumentResolvers);
    }

    @Override
    public void setCustomReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        exceptionHandlerExceptionResolver.setCustomReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        exceptionHandlerExceptionResolver.setMessageConverters(messageConverters);
    }

    @Override
    public void setResponseBodyAdvice(List<ResponseBodyAdvice<?>> responseBodyAdvice) {
        exceptionHandlerExceptionResolver.setResponseBodyAdvice(responseBodyAdvice);
    }

    @Override
    public void setReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        exceptionHandlerExceptionResolver.setReturnValueHandlers(returnValueHandlers);
    }

    @Override
    public void afterPropertiesSet() {
        exceptionHandlerExceptionResolver.afterPropertiesSet();
    }
}
