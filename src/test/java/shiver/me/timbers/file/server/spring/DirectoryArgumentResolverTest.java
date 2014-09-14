package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import shiver.me.timbers.file.io.Directory;
import shiver.me.timbers.file.io.File;

import static org.mockito.Mockito.mock;

public class DirectoryArgumentResolverTest {

    private static final DirectoryArgumentResolver RESOLVER = new DirectoryArgumentResolver();

    @Test
    @SuppressWarnings("unchecked")
    public void I_can_check_the_supported_argument_type() {

        ArgumentResolverSteps.I_can_check_the_supported_argument_type(RESOLVER, Directory.class, File.class,
                String.class, Integer.class, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_check_a_null_argument_type() {

        ArgumentResolverSteps.I_cannot_check_a_null_argument_type(RESOLVER);
    }

    @Test
    public void I_can_resolve_a_directory_from_the_request() throws Exception {

        final Directory directory = mock(Directory.class);

        ArgumentResolverSteps.I_can_resolve_a_directory_from_the_request(RESOLVER, "directory", directory);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_resolve_a_directory_from_a_null_request() throws Exception {

        ArgumentResolverSteps.I_cannot_resolve_a_directory_from_a_null_request(RESOLVER);
    }
}
