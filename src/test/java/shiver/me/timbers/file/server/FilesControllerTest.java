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
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE_NAME;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT_CONTENT;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE_TEXT;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE_TEXT;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN_CONTENT;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX_ABSOLUTE_PATH;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX_TEXT;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FilesControllerTest {

    private static final String ATTRIBUTE_NAME = "absolutePath";
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

        mockMvc.perform(get("/directory").requestAttr(ATTRIBUTE_NAME, DIRECTORY_ONE_ABSOLUTE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value(DIRECTORY_ONE_NAME))
                .andExpect(jsonPath("$.modified").exists());
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

        mockMvc.perform(get("/file").requestAttr(ATTRIBUTE_NAME, FILE_ONE_ABSOLUTE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(FILE_ONE_TEXT));
    }

    @Test
    public void I_can_request_a_json_file() throws Exception {

        mockMvc.perform(get("/file").requestAttr(ATTRIBUTE_NAME, FILE_FIVE_ABSOLUTE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(content().string(FILE_FIVE_TEXT));
    }

    @Test
    public void I_can_request_an_xml_file() throws Exception {

        mockMvc.perform(get("/file").requestAttr(ATTRIBUTE_NAME, FILE_SIX_ABSOLUTE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML_VALUE))
                .andExpect(content().string(FILE_SIX_TEXT));
    }

    @Test
    public void I_can_request_a_png_file() throws Exception {

        mockMvc.perform(get("/file").requestAttr(ATTRIBUTE_NAME, FILE_SEVEN_ABSOLUTE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(IMAGE_PNG))
                .andExpect(content().bytes(FILE_SEVEN_CONTENT));
    }

    @Test
    public void I_can_request_a_video_file() throws Exception {

        mockMvc.perform(get("/file").requestAttr(ATTRIBUTE_NAME, FILE_EIGHT_ABSOLUTE_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("video/mp4"))
                .andExpect(content().bytes(FILE_EIGHT_CONTENT));
    }

    @Test
    public void I_cannot_request_a_file_without_a_path() throws Exception {

        mockMvc.perform(get("/file"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(ERROR_MESSAGE));
    }
}
