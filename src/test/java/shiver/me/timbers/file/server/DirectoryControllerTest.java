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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_TWO;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO;
import static shiver.me.timbers.file.server.FilesRoutingController.ABSOLUTE_PATH;
import static shiver.me.timbers.file.server.ServerConstants.ERROR_MESSAGE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class DirectoryControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void I_can_request_a_directory() throws Exception {

        mockMvc.perform(get("/directory").requestAttr(ABSOLUTE_PATH, DIRECTORY_ONE.getAbsolutePath()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value(DIRECTORY_ONE.getName()))
                .andExpect(jsonPath("$.modified").exists())
                .andExpect(jsonPath("$.directories").isArray())
                .andExpect(jsonPath("$.directories[0].name").value(DIRECTORY_TWO.getName()))
                .andExpect(jsonPath("$.directories[0].modified").exists())
                .andExpect(jsonPath("$.files").isArray())
                .andExpect(jsonPath("$.files[0].name").value(FILE_TWO.getName()))
                .andExpect(jsonPath("$.files[0].modified").value(FILE_TWO.getModified().getTime()))
                .andExpect(jsonPath("$.files[0].extension").value(FILE_TWO.getExtension()))
                .andExpect(jsonPath("$.files[0].size").value(Long.valueOf(FILE_TWO.getSize()).intValue()));
    }

    @Test
    public void I_cannot_request_a_directory_without_a_path() throws Exception {

        mockMvc.perform(get("/directory"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(ERROR_MESSAGE));
    }
}
