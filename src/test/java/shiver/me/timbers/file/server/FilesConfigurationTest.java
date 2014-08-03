package shiver.me.timbers.file.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertNotNull;

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
    private FilesControllerAdvice filesControllerAdvice;

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
    public void I_can_access_the_files_controller_advice() {

        assertNotNull("the files routing controller should be available.", filesControllerAdvice);
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
}
