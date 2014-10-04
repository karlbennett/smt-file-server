package shiver.me.timbers.file.server.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.WrappedExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import shiver.me.timbers.file.server.spring.controller.DirectoryArgumentResolver;
import shiver.me.timbers.file.server.spring.controller.RangeFileHttpMessageConverter;
import shiver.me.timbers.file.server.spring.controller.RangesArgumentResolver;
import shiver.me.timbers.file.server.spring.controller.RangesFileHttpMessageConverter;
import shiver.me.timbers.file.server.spring.controller.StreamFileArgumentResolver;
import shiver.me.timbers.file.server.spring.controller.StreamFileHttpMessageConverter;
import shiver.me.timbers.file.server.spring.exception.DefaultAwareExceptionHandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

/**
 * Configuration class used to configure the Spring framework.
 *
 * @author Karl Bennett
 */
@Configuration
@ComponentScan("shiver.me.timbers.file.server")
public class FilesConfiguration extends WebMvcConfigurationSupport {

    /**
     * @return the path of the directory that this application started up in. It is then used as the base for all
     *         subsequents resource requests.
     * @throws IOException if the root path does not exist.
     */
    @Bean
    public static String rootPath() throws IOException {

        return new java.io.File("").getCanonicalPath();
    }

    @Override
    protected void configureViewResolvers(ViewResolverRegistry registry) {

        final UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setViewClass(InternalResourceView.class);

        registry.viewResolver(resolver);
    }

    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        configurer.favorPathExtension(false);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(new RangesFileHttpMessageConverter());
        converters.add(new RangeFileHttpMessageConverter());
        converters.add(new StreamFileHttpMessageConverter<>());

        addDefaultHttpMessageConverters(converters);
    }

    @Override
    protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
        super.addDefaultHandlerExceptionResolvers(exceptionResolvers);

        HandlerExceptionResolver defaultHandlerExceptionResolver = findHandlerExceptionResolver(
                DefaultHandlerExceptionResolver.class, exceptionResolvers);

        ExceptionHandlerExceptionResolver old = findHandlerExceptionResolver(
                ExceptionHandlerExceptionResolver.class, exceptionResolvers);

        DefaultAwareExceptionHandlerExceptionResolver current =
                new DefaultAwareExceptionHandlerExceptionResolver(defaultHandlerExceptionResolver,
                        new WrappedExceptionHandlerExceptionResolver(old));

        exceptionResolvers.set(0, current);
    }

    @SuppressWarnings("unchecked")
    public static <T extends HandlerExceptionResolver> T findHandlerExceptionResolver(
            Class<T> type, List<HandlerExceptionResolver> exceptionResolvers) {

        for (HandlerExceptionResolver exceptionResolver : exceptionResolvers) {

            if (type.isAssignableFrom(exceptionResolver.getClass())) {
                return (T) exceptionResolver;
            }
        }

        throw new IllegalArgumentException("Cannot find HandlerExceptionResolver of type " + type.getName());
    }

    @Override
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(new DirectoryArgumentResolver());
        argumentResolvers.add(new StreamFileArgumentResolver());
        argumentResolvers.add(new RangesArgumentResolver());
    }
}
