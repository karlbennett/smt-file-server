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
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static shiver.me.timbers.file.server.spring.config.FilesConfiguration.findType;

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
    public void I_can_find_a_type() {

        class TypeOne {
        }

        class TypeTwo {
        }

        class TypeThree extends TypeTwo {
        }

        class TypeFour {
        }


        final TypeOne typeOne = mock(TypeOne.class);
        final TypeThree typethree = mock(TypeThree.class);

        final List<Object> resolvers = asList(typeOne, typethree, mock(TypeFour.class));

        assertEquals("type one should be found", typeOne, findType(TypeOne.class, resolvers));
        assertEquals("type three should be found", typethree, findType(TypeTwo.class, resolvers));
        assertEquals("type three should be found", typethree, findType(TypeThree.class, resolvers));
    }

    @Test(expected = IllegalArgumentException.class)
    public void I_cannot_find_a_handler_exception_resolver_that_does_not_exist() {

        final List<HandlerExceptionResolver> resolvers = new ArrayList<>();
        resolvers.add(mock(HandlerExceptionResolver.class));

        findType(DefaultHandlerExceptionResolver.class, resolvers);
    }
}
