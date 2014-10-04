package shiver.me.timbers.file.server.spring.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import shiver.me.timbers.file.server.spring.controller.DirectoryController;
import shiver.me.timbers.file.server.spring.controller.FileController;
import shiver.me.timbers.file.server.spring.controller.FilesRoutingController;
import shiver.me.timbers.file.server.spring.controller.GlobalControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.file.server.spring.config.FilesConfiguration.findHandlerExceptionResolver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FilesConfigurationTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private GlobalControllerAdvice globalControllerAdvice;

    @Autowired
    private FilesRoutingController filesRoutingController;

    @Autowired
    private DirectoryController directoryController;

    @Autowired
    private FileController fileController;

    @Autowired
    private String rootPath;

    @Test
    public void I_can_access_the_spring_context() {

        assertNotNull("the spring context should be available.", context);
    }

    @Test
    public void I_can_access_the_global_controller_advice() {

        assertNotNull("the global controller advice should be available.", globalControllerAdvice);
    }

    @Test
    public void I_can_access_the_files_routing_controller() {

        assertNotNull("the files routing controller should be available.", filesRoutingController);
    }

    @Test
    public void I_can_access_the_file_controller() {

        assertNotNull("the file controller should be available.", fileController);
    }

    @Test
    public void I_can_access_the_directory_controller() {

        assertNotNull("the directory controller should be available.", directoryController);
    }

    @Test
    public void I_can_access_the_root_path() {

        assertNotNull("the root path should be available.", rootPath);
    }

    @Test
    public void I_can_find_a_handler_exception_resolver() {

        final DefaultHandlerExceptionResolver defaultHandlerExceptionResolver = mock(DefaultHandlerExceptionResolver.class);
        final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = mock(ExceptionHandlerExceptionResolver.class);

        final List<HandlerExceptionResolver> resolvers = new ArrayList<>();
        resolvers.add(defaultHandlerExceptionResolver);
        resolvers.add(exceptionHandlerExceptionResolver);
        resolvers.add(mock(HandlerExceptionResolver.class));

        assertEquals("the default handler exception resolver should be found", defaultHandlerExceptionResolver,
                findHandlerExceptionResolver(DefaultHandlerExceptionResolver.class, resolvers));
        assertEquals("the exception handler exception resolver should be found", exceptionHandlerExceptionResolver,
                findHandlerExceptionResolver(ExceptionHandlerExceptionResolver.class, resolvers));
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_find_a_handler_exception_resolver_that_does_not_exist() {

        final List<HandlerExceptionResolver> resolvers = new ArrayList<>();
        resolvers.add(mock(HandlerExceptionResolver.class));

        findHandlerExceptionResolver(DefaultHandlerExceptionResolver.class, resolvers);
    }
}
