package shiver.me.timbers.file.server.spring.controller;

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
import shiver.me.timbers.file.io.JavaDirectory;
import shiver.me.timbers.file.server.spring.config.FilesConfiguration;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_TWO;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO;
import static shiver.me.timbers.file.server.spring.controller.Requests.DIRECTORY;

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

        mockMvc.perform(get("/directory").requestAttr(DIRECTORY, new JavaDirectory(DIRECTORY_ONE.getAbsolutePath())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value(DIRECTORY_ONE.getName()))
                .andExpect(jsonPath("$.modified").exists())
                .andExpect(jsonPath("$.directories").isArray())
                .andExpect(jsonPath("$.directories").value(hasSize(1)))
                .andExpect(jsonPath("$.directories[0].name").value(DIRECTORY_TWO.getName()))
                .andExpect(jsonPath("$.directories[0].modified").exists())
                .andExpect(jsonPath("$.files").isArray())
                .andExpect(jsonPath("$.files").value(hasSize(1)))
                .andExpect(jsonPath("$.files[0].name").value(FILE_TWO.getName()))
                .andExpect(jsonPath("$.files[0].modified").value(FILE_TWO.getModified().getTime()))
                .andExpect(jsonPath("$.files[0].extension").value(FILE_TWO.getExtension()))
                .andExpect(jsonPath("$.files[0].size").value((int) FILE_TWO.getSize()))
                .andExpect(jsonPath("$.files[0].mimeType").value(FILE_TWO.getMimeType()));
    }

    @Test
    public void I_can_request_a_directory_above_the_root_directory() throws Exception {

        // The path boundary check is done in the FilesRoutingController so it should not be possible to get here with
        // an illegal path so no check is carried out in this controller.
        mockMvc.perform(get("/directory").requestAttr(DIRECTORY, new JavaDirectory("../")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE));
    }

    @Test
    public void I_cannot_request_a_directory_without_a_path() throws Exception {

        mockMvc.perform(get("/directory"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value("No directory provided."));
    }
}
