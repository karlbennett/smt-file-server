package org.springframework.web.servlet.mvc.method.annotation;

import org.junit.Before;
import org.junit.Test;
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
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class WrappedExceptionHandlerExceptionResolverTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private HandlerMethod handlerMethod;
    private Exception exception;

    private ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver;
    private WrappedExceptionHandlerExceptionResolver resolver;

    @Before
    public void setUp() {

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        handlerMethod = mock(HandlerMethod.class);
        exception = mock(Exception.class);

        exceptionHandlerExceptionResolver = mock(WrappedExceptionHandlerExceptionResolver.class);
        resolver = new WrappedExceptionHandlerExceptionResolver(exceptionHandlerExceptionResolver);
    }

    @Test
    public void afterPropertiesSet() {

        resolver.afterPropertiesSet();

        verify(exceptionHandlerExceptionResolver, times(1)).afterPropertiesSet();
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }

    @Test
    public void I_can_resolve_a_handler_method_exception()
            throws NoSuchMethodException, UnsupportedEncodingException {

        final ModelAndView expected = mock(ModelAndView.class);

        when(exceptionHandlerExceptionResolver
                .doResolveHandlerMethodException(request, response, handlerMethod, exception)).thenReturn(expected);

        final ModelAndView actual =
                resolver.doResolveHandlerMethodException(request, response, handlerMethod, exception);

        assertEquals("the returned model and view should be correct", expected, actual);
    }

    @Test
    public void I_can_get_an_application_context() {

        final ApplicationContext expected = mock(ApplicationContext.class);

        when(exceptionHandlerExceptionResolver.getApplicationContext()).thenReturn(expected);

        final ApplicationContext actual = resolver.getApplicationContext();

        assertEquals("the correct context should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_argument_resolvers() {

        final HandlerMethodArgumentResolverComposite expected = mock(HandlerMethodArgumentResolverComposite.class);

        when(exceptionHandlerExceptionResolver.getArgumentResolvers()).thenReturn(expected);

        final HandlerMethodArgumentResolverComposite actual = resolver.getArgumentResolvers();

        assertEquals("the correct argument resolver should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_a_content_negotiation_manager() {

        final ContentNegotiationManager expected = mock(ContentNegotiationManager.class);

        when(exceptionHandlerExceptionResolver.getContentNegotiationManager()).thenReturn(expected);

        final ContentNegotiationManager actual = resolver.getContentNegotiationManager();

        assertEquals("the correct negotiation manager should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_custom_argument_resolvers() {

        @SuppressWarnings("unchecked")
        final List<HandlerMethodArgumentResolver> expected = mock(List.class);

        when(exceptionHandlerExceptionResolver.getCustomArgumentResolvers()).thenReturn(expected);

        final List<HandlerMethodArgumentResolver> actual = resolver.getCustomArgumentResolvers();

        assertEquals("the correct argument resolvers should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_custom_return_value_handlers() {

        @SuppressWarnings("unchecked")
        final List<HandlerMethodReturnValueHandler> expected = mock(List.class);

        when(exceptionHandlerExceptionResolver.getCustomReturnValueHandlers()).thenReturn(expected);

        final List<HandlerMethodReturnValueHandler> actual = resolver.getCustomReturnValueHandlers();

        assertEquals("the correct return value handlers should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_default_argument_resolvers() {

        @SuppressWarnings("unchecked")
        final List<HandlerMethodArgumentResolver> expected = mock(List.class);

        when(exceptionHandlerExceptionResolver.getDefaultArgumentResolvers()).thenReturn(expected);

        final List<HandlerMethodArgumentResolver> actual = resolver.getDefaultArgumentResolvers();

        assertEquals("the correct argument resolvers should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_default_return_value_handlers() {

        @SuppressWarnings("unchecked")
        final List<HandlerMethodReturnValueHandler> expected = mock(List.class);

        when(exceptionHandlerExceptionResolver.getDefaultReturnValueHandlers()).thenReturn(expected);

        final List<HandlerMethodReturnValueHandler> actual = resolver.getDefaultReturnValueHandlers();

        assertEquals("the correct return value handlers should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_an_exception_handler_advice_cache() {

        @SuppressWarnings("unchecked")
        final Map<ControllerAdviceBean, ExceptionHandlerMethodResolver> expected = mock(Map.class);

        when(exceptionHandlerExceptionResolver.getExceptionHandlerAdviceCache()).thenReturn(expected);

        final Map<ControllerAdviceBean, ExceptionHandlerMethodResolver> actual =
                resolver.getExceptionHandlerAdviceCache();

        assertEquals("the correct exception handler cache should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_an_exception_handler_method() {

        final HandlerMethod handlerMethod = mock(HandlerMethod.class);
        final Exception exception = mock(Exception.class);

        final ServletInvocableHandlerMethod expected = mock(ServletInvocableHandlerMethod.class);

        when(exceptionHandlerExceptionResolver.getExceptionHandlerMethod(handlerMethod, exception))
                .thenReturn(expected);

        final ServletInvocableHandlerMethod actual = resolver.getExceptionHandlerMethod(handlerMethod, exception);

        assertEquals("the correct handler method should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_message_converters() {

        @SuppressWarnings("unchecked")
        final List<HttpMessageConverter<?>> expected = mock(List.class);

        when(exceptionHandlerExceptionResolver.getMessageConverters()).thenReturn(expected);

        final List<HttpMessageConverter<?>> actual = resolver.getMessageConverters();

        assertEquals("the correct message converters should be returned.", expected, actual);
    }

    @Test
    public void I_can_get_return_value_handlers() {

        final HandlerMethodReturnValueHandlerComposite expected = mock(HandlerMethodReturnValueHandlerComposite.class);

        when(exceptionHandlerExceptionResolver.getReturnValueHandlers()).thenReturn(expected);

        final HandlerMethodReturnValueHandlerComposite actual = resolver.getReturnValueHandlers();

        assertEquals("the correct return value handler should be returned.", expected, actual);
    }

    @Test
    public void I_can_set_an_application_context() {

        ApplicationContext applicationContext = mock(ApplicationContext.class);

        resolver.setApplicationContext(applicationContext);

        verify(exceptionHandlerExceptionResolver, times(1)).setApplicationContext(applicationContext);
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }

    @Test
    public void I_can_set_argument_resolvers() {

        @SuppressWarnings("unchecked")
        List<HandlerMethodArgumentResolver> argumentResolvers = mock(List.class);

        resolver.setArgumentResolvers(argumentResolvers);

        verify(exceptionHandlerExceptionResolver, times(1)).setArgumentResolvers(argumentResolvers);
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }

    @Test
    public void I_can_set_a_content_negotiation_manager() {

        ContentNegotiationManager contentNegotiationManager = mock(ContentNegotiationManager.class);

        resolver.setContentNegotiationManager(contentNegotiationManager);

        verify(exceptionHandlerExceptionResolver, times(1)).setContentNegotiationManager(contentNegotiationManager);
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }

    @Test
    public void I_can_set_custom_argument_resolvers() {

        @SuppressWarnings("unchecked")
        List<HandlerMethodArgumentResolver> argumentResolvers = mock(List.class);

        resolver.setCustomArgumentResolvers(argumentResolvers);

        verify(exceptionHandlerExceptionResolver, times(1)).setCustomArgumentResolvers(argumentResolvers);
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }

    @Test
    public void I_can_set_custom_return_value_handlers() {

        @SuppressWarnings("unchecked")
        List<HandlerMethodReturnValueHandler> returnValueHandlers = mock(List.class);

        resolver.setCustomReturnValueHandlers(returnValueHandlers);

        verify(exceptionHandlerExceptionResolver, times(1)).setCustomReturnValueHandlers(returnValueHandlers);
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }

    @Test
    public void I_can_set_message_converters() {

        @SuppressWarnings("unchecked")
        List<HttpMessageConverter<?>> messageConverters = mock(List.class);

        resolver.setMessageConverters(messageConverters);

        verify(exceptionHandlerExceptionResolver, times(1)).setMessageConverters(messageConverters);
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }

    @Test
    public void I_can_set_a_response_body_advice() {

        @SuppressWarnings("unchecked")
        List<ResponseBodyAdvice<?>> responseBodyAdvice = mock(List.class);

        resolver.setResponseBodyAdvice(responseBodyAdvice);

        verify(exceptionHandlerExceptionResolver, times(1)).setResponseBodyAdvice(responseBodyAdvice);
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }

    @Test
    public void I_can_set_return_value_handlers() {

        @SuppressWarnings("unchecked")
        List<HandlerMethodReturnValueHandler> returnValueHandlers = mock(List.class);

        resolver.setReturnValueHandlers(returnValueHandlers);

        verify(exceptionHandlerExceptionResolver, times(1)).setReturnValueHandlers(returnValueHandlers);
        verifyNoMoreInteractions(exceptionHandlerExceptionResolver);
    }
}
