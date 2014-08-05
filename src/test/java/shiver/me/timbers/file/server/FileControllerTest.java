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
import java.util.Arrays;

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
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX;
import static shiver.me.timbers.file.server.FilesRoutingController.ABSOLUTE_PATH;
import static shiver.me.timbers.file.server.ServerConstants.ERROR_MESSAGE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FileControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(new AcceptRangesFilter()).build();
    }

    @Test
    public void I_can_check_a_file() throws Exception {

        mockMvcForFile(request(HEAD, "/file").requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath()), FILE_ONE)
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(""));
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
    public void I_cannot_request_a_file_without_an_invalid_path() throws Exception {

        mockMvc.perform(get("/file").requestAttr(ABSOLUTE_PATH, "invalid"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value("No such file or directory."));
    }

    @Test
    public void I_cannot_request_a_file_without_a_path() throws Exception {

        mockMvc.perform(get("/file"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(ERROR_MESSAGE));
    }

    @Test
    public void I_can_check_a_partial_file() throws Exception {

        mockMvcHeadersForFile(
                request(HEAD, "/file")
                        .requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath())
                        .header("Range", "bytes=0-10"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes 0-10/%d", FILE_ONE.getSize())))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(""));
    }

    @Test
    public void I_can_request_a_partial_file() throws Exception {

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath())
                        .header("Range", "bytes=6-12"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes 6-12/%d", FILE_ONE.getSize())))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("ile one"));
    }

    @Test
    public void I_can_request_the_last_partial_file_with_an_end_that_is_too_large() throws Exception {

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath())
                        .header("Range", "bytes=6-1000000"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes 6-13/%d", FILE_ONE.getSize())))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string("ile one."));
    }

    @Test
    public void I_can_request_all_of_the_file_with_a_zero_start_and_an_end_that_is_too_large() throws Exception {

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath())
                        .header("Range", "bytes=0-1000000"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes 0-13/%d", FILE_ONE.getSize())))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(FILE_ONE.getContent()));
    }

    @Test
    public void I_can_request_a_partial_file_with_a_large_range() throws Exception {

        final int start = 10000;
        final int end = 100500;

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(ABSOLUTE_PATH, FILE_EIGHT.getAbsolutePath())
                        .header("Range", format("bytes=%d-%d", start, end)),
                FILE_EIGHT
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes %d-%d/%d", start, end, FILE_EIGHT.getSize())))
                .andExpect(content().contentType("video/mp4"))
                .andExpect(content().bytes(Arrays.copyOfRange(FILE_EIGHT.getContent(), start, end + 1)));
    }

    @Test
    public void I_can_check_a_partial_file_with_a_range_value_with_a_start_value_greater_than_the_end_value()
            throws Exception {

        mockMvcHeadersForFile(
                request(HEAD, "/file")
                        .requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath())
                        .header("Range", "bytes=10-0"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().doesNotExist("Content-Range"))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(""));
    }

    @Test
    public void I_can_request_a_partial_file_with_a_range_value_with_a_start_value_greater_than_the_end_value()
            throws Exception {

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath())
                        .header("Range", "bytes=10-0"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().doesNotExist("Content-Range"))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(FILE_ONE.getContent()));
    }

    @Test
    public void I_cannot_request_a_partial_file_with_no_ranges() throws Exception {

        mockMvc.perform(get("/file")
                .requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath())
                .header("Range", "bytes=")
        ).andExpect(status().isRequestedRangeNotSatisfiable())
                // FIXME: Should be the below media type.
                // .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(jsonPath("$.error").value(format(
                        "The supplied Range header is either malformed or out of bounds. Range: bytes=, File Size: %d",
                        FILE_ONE.getSize())));
    }

    @Test
    public void I_cannot_request_a_partial_file_with_an_invalid_range() throws Exception {

        mockMvc.perform(get("/file")
                .requestAttr(ABSOLUTE_PATH, FILE_ONE.getAbsolutePath())
                .header("Range", "bytes=1000-1001")
        ).andExpect(status().isRequestedRangeNotSatisfiable())
                // FIXME: Should be the below media type.
                // .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(jsonPath("$.error").value(format(
                        "The supplied Range header is either malformed or out of bounds. Range: 1000-1001, File Size: %d",
                        FILE_ONE.getSize())));
    }

    @Test
    public void I_cannot_request_a_partial_file_with_an_invalid_absolute_path() throws Exception {

        mockMvc.perform(get("/file")
                .requestAttr(ABSOLUTE_PATH, "invalid")
                .header("Range", "bytes=0-10")
        ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No such file or directory."));
    }

    private ResultActions mockMvcForFile(TestFile file) throws Exception {

        return mockMvcForFile(get("/file").requestAttr(ABSOLUTE_PATH, file.getAbsolutePath()), file);
    }

    private ResultActions mockMvcForFile(MockHttpServletRequestBuilder requestBuilder, TestFile file) throws Exception {

        return mockMvcHeadersForFile(requestBuilder, file)
                .andExpect(status().isOk());
    }

    private ResultActions mockMvcHeadersForFile(MockHttpServletRequestBuilder requestBuilder, TestFile file) throws Exception {

        final DateFormat HTTP_DATE = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");

        return mockMvc.perform(requestBuilder)
                .andExpect(header().string("Accept-Ranges", "bytes"))
                .andExpect(header().string("ETag", format("\"%s_%d_%d\"", file.getName(), file.getSize(),
                        file.getModified().getTime())))
                .andExpect(header().string("Last-Modified", HTTP_DATE.format(file.getModified())));
    }
}
