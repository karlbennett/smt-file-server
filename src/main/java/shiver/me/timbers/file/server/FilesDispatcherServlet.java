package shiver.me.timbers.file.server;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * This servlet will be automatically registered within any JEE6 container. This will then start up the Spring
 * framework.
 *
 * @author Karl Bennett
 */
@WebServlet(
        value = "/*",
        initParams = {
                // Enable Spring Java configuration.
                @WebInitParam(
                        name = "contextClass",
                        value = "org.springframework.web.context.support.AnnotationConfigWebApplicationContext"
                ),
                // Tell Spring where the Java configuration file is.
                @WebInitParam(
                        name = "contextConfigLocation",
                        value = "shiver.me.timbers.file.server.FilesConfiguration"
                )},
        asyncSupported = true
)
public class FilesDispatcherServlet extends DispatcherServlet {
}
