package shiver.me.timbers.file.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;
import java.io.IOException;

/**
 * Configuration class used to configure the Spring framework.
 *
 * @author Karl Bennett
 */
@Configuration
@EnableWebMvc // Enable the Spring Web MVC environment, this includes support for XML/JSON conversion and validation.
// Tell Spring which package to look in for controller classes. This has been done by providing a class from the
// required package.
@ComponentScan("shiver.me.timbers.file.server")
public class FilesConfiguration extends WebMvcConfigurerAdapter {

    /**
     * @return the path of the directory that this application started up in. It is then used as the base for all
     *         subsequents resource requests.
     */
    @Bean
    public static String rootPath() throws IOException {

        return new File("").getCanonicalPath();
    }

    /**
     * Add all the interceptors that provide cross cutting logic to multiple endpoints e.g.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new PartialRequestHeadersHandlerInterceptor()).addPathPatterns("/file");
    }
}
