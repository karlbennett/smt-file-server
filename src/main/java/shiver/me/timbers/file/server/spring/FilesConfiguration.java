package shiver.me.timbers.file.server.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.io.IOException;
import java.util.List;

/**
 * Configuration class used to configure the Spring framework.
 *
 * @author Karl Bennett
 */
@Configuration
// Tell Spring which package to look in for controller classes.
@ComponentScan("shiver.me.timbers.file.server")
// Spring MVC is enabled by extending WebMvcConfigurationSupport
public class FilesConfiguration extends WebMvcConfigurationSupport {

    /**
     * @return the path of the directory that this application started up in. It is then used as the base for all
     *         subsequents resource requests.
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
    protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        argumentResolvers.add(new DirectoryArgumentResolver());
        argumentResolvers.add(new StreamFileArgumentResolver());
        argumentResolvers.add(new RangesArgumentResolver());
    }
}
