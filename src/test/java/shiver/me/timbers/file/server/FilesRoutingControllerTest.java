package shiver.me.timbers.file.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FilesRoutingControllerTest {

    private static final String ERROR_MESSAGE = "No such file or directory.";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void I_can_route_to_a_directory() throws Exception {

        mockMvc.perform(get(DIRECTORY_ONE.getPath()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/directory"));
    }

    @Test
    public void I_can_route_to_a_file() throws Exception {

        mockMvc.perform(get(FILE_ONE.getPath()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/file"));
    }

    @Test
    public void I_can_route_to_a_file_check() throws Exception {

        mockMvc.perform(request(HEAD, FILE_ONE.getPath()))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/file"));
    }

    @Test
    public void I_cannot_route_to_an_invalid_path() throws Exception {

        mockMvc.perform(get("invalid"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(ERROR_MESSAGE));
    }
}
