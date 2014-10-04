package org.springframework.web.servlet.mvc.method.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.method.ControllerAdviceBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * This {@link ExceptionHandlerExceptionResolver} simply exists to allow the delegation to all the
 * {@link ExceptionHandlerExceptionResolver} methods. Quite a few methods are {@code protected} so cannot be called for
 * delegation.
 *
 * @author Karl Bennett
 */
public class WrappedExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

    private final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver;

    public WrappedExceptionHandlerExceptionResolver(
            ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver) {

        this.exceptionHandlerExceptionResolver = exceptionHandlerExceptionResolver;
    }

    @Override
    public ModelAndView doResolveHandlerMethodException(HttpServletRequest request,
                                                        HttpServletResponse response, HandlerMethod handlerMethod,
                                                        Exception exception) {

        return exceptionHandlerExceptionResolver.doResolveHandlerMethodException(request, response, handlerMethod,
                exception);
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
