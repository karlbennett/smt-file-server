package shiver.me.timbers.file.server;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import shiver.me.timbers.file.io.TestFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_ONE;
import static shiver.me.timbers.file.io.DirectoryConstants.DIRECTORY_TWO;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX;
import static shiver.me.timbers.file.io.FileConstants.FILE_TWO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FilesControllerTest {

    private static final String ABSOLUTE_PATH = "absolutePath";
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

    @Test
    public void I_can_check_a_file() throws Exception {

        mockMvcForFile(request(HEAD, "/file").requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath()), FILE_ONE)
                .andExpect(content().contentType(TEXT_PLAIN));
    }

    @Test
    public void I_can_request_a_file() throws Exception {

        mockMvcForFile(FILE_ONE)
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(FILE_ONE.getContent()));
    }

    @Test
    public void I_can_request_a_json_file() throws Exception {

        mockMvcForFile(FILE_FIVE)
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(content().string(FILE_FIVE.getContent()));
    }

    @Test
    public void I_can_request_an_xml_file() throws Exception {

        mockMvcForFile(FILE_SIX)
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML_VALUE))
                .andExpect(content().string(FILE_SIX.getContent()));
    }

    @Test
    public void I_can_request_a_png_file() throws Exception {

        mockMvcForFile(FILE_SEVEN)
                .andExpect(content().contentTypeCompatibleWith(IMAGE_PNG))
                .andExpect(content().bytes(FILE_SEVEN.getContent()));
    }

    @Test
    public void I_can_request_a_video_file() throws Exception {

        mockMvcForFile(FILE_EIGHT)
                .andExpect(content().contentTypeCompatibleWith("video/mp4"))
                .andExpect(content().bytes(FILE_EIGHT.getContent()));
    }

    @Test
    public void I_cannot_request_a_file_without_a_path() throws Exception {

        mockMvc.perform(get("/file"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(ERROR_MESSAGE));
    }

    private ResultActions mockMvcForFile(TestFile file) throws Exception {

        return mockMvcForFile(get("/file").requestAttr(ABSOLUTE_PATH, file.getAbsolutePath()), file);
    }

    private ResultActions mockMvcForFile(MockHttpServletRequestBuilder requestBuilder, TestFile file) throws Exception {

        final DateFormat HTTP_DATE = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

        return mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(header().string("Accept-Ranges", "bytes"))
                .andExpect(header().string("ETag", format("\"%s_%d_%d\"", file.getName(), file.getSize(),
                        file.getModified().getTime())))
                .andExpect(header().string("Last-Modified", HTTP_DATE.format(file.getModified())));
    }
}
