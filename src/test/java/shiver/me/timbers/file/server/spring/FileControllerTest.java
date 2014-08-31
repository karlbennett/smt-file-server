package shiver.me.timbers.file.server.spring;

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
import shiver.me.timbers.file.io.JavaStreamFile;
import shiver.me.timbers.file.io.TestFile;

import static java.lang.String.format;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX;
import static shiver.me.timbers.file.server.ServerConstants.dateFormat;
import static shiver.me.timbers.file.server.spring.Requests.FILE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FileControllerTest {

    private static final String NO_FILE_ERROR_MESSAGE = "No file provided.";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void I_can_check_a_file() throws Exception {

        mockMvcForFile(request(HEAD, "/file").requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath())), FILE_ONE)
                .andExpect(content().string(""));
    }

    @Test
    public void I_can_request_a_file() throws Exception {

        mockMvcForStringFile(FILE_ONE);
    }

    @Test
    public void I_can_request_a_json_file() throws Exception {

        mockMvcForStringFile(FILE_FIVE);
    }

    @Test
    public void I_can_request_an_xml_file() throws Exception {

        mockMvcForStringFile(FILE_SIX);
    }

    @Test
    public void I_can_request_a_png_file() throws Exception {

        mockMvcForBinaryFile(FILE_SEVEN);
    }

    @Test
    public void I_can_request_a_video_file() throws Exception {

        mockMvcForBinaryFile(FILE_EIGHT);
    }

    @Test
    public void I_can_request_a_file_above_the_root_directory() throws Exception {

        final JavaStreamFile file = new JavaStreamFile("../pom.xml");

        // The path boundary check is done in the FilesRoutingController so it should not be possible to get here with
        // an illegal path so no check is carried out in this controller.
        mockMvc.perform(get("/file").requestAttr(FILE, file))
                .andExpect(status().isOk())
                .andExpect(content().contentType(file.getMimeType()))
                .andExpect(header().string("Accept-Ranges", "bytes"))
                .andExpect(header().string("ETag", format("\"%s_%d_%d\"", file.getName(), file.getSize(),
                        file.getModified().getTime())))
                .andExpect(header().string("Last-Modified", dateFormat().format(file.getModified())));
    }

    @Test
    public void I_cannot_request_a_file_without_a_path() throws Exception {

        mockMvc.perform(get("/file"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(NO_FILE_ERROR_MESSAGE));
    }

    private ResultActions mockMvcForStringFile(TestFile<String> file) throws Exception {

        return mockMvcForFile(file).andExpect(content().string(file.getContent()));
    }

    private ResultActions mockMvcForBinaryFile(TestFile<byte[]> file) throws Exception {

        return mockMvcForFile(file).andExpect(content().bytes(file.getContent()));
    }

    private ResultActions mockMvcForFile(TestFile file) throws Exception {

        return mockMvcForFile(get("/file").requestAttr(FILE, new JavaStreamFile(file.getAbsolutePath())), file);
    }

    private ResultActions mockMvcForFile(MockHttpServletRequestBuilder requestBuilder, TestFile file) throws Exception {

        return mockMvcHeadersForFile(requestBuilder, file)
                .andExpect(status().isOk());
    }

    private ResultActions mockMvcHeadersForFile(MockHttpServletRequestBuilder requestBuilder, TestFile file)
            throws Exception {

        return Controllers.mockMvcHeadersForFile(mockMvc, requestBuilder, file)
                .andExpect(content().contentTypeCompatibleWith(file.getMediaType()));
    }
}
