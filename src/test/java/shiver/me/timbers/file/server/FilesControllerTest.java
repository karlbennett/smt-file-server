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
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.Constants.DIRECTORY_FOUR_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_NAME;
import static shiver.me.timbers.Constants.FILE_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.FILE_ONE_TEXT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FilesControllerTest {

    private static final String ERROR_MESSAGE = "No path provided.";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void I_can_request_a_directory() throws Exception {

        mockMvc.perform(get("/directory").requestAttr("path", DIRECTORY_ONE_ABSOLUTE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value(DIRECTORY_ONE_NAME))
                .andExpect(jsonPath("$.extension").value(""))
                .andExpect(jsonPath("$.modified").value(DIRECTORY_FOUR_MODIFICATION_DATE.getTime()));
    }

    @Test
    public void I_cannot_request_a_directory_without_a_path() throws Exception {

        mockMvc.perform(get("/directory"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(ERROR_MESSAGE));
    }

    @Test
    public void I_can_request_a_file() throws Exception {

        mockMvc.perform(get("/file").requestAttr("path", FILE_ONE_ABSOLUTE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(FILE_ONE_TEXT));
    }

    @Test
    public void I_cannot_request_a_file_without_a_path() throws Exception {

        mockMvc.perform(get("/file"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(ERROR_MESSAGE));
    }
}
