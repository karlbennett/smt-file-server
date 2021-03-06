package shiver.me.timbers.file.server.spring.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import shiver.me.timbers.file.io.JavaStreamFile;
import shiver.me.timbers.file.io.TestFile;
import shiver.me.timbers.file.server.spring.config.FilesConfiguration;

import java.util.Arrays;

import static java.lang.String.format;
import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.Entry;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.server.spring.controller.Controllers.buildContent;
import static shiver.me.timbers.file.server.spring.controller.Requests.FILE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FileControllerRangeTest {

    private static final String NO_FILE_ERROR_MESSAGE = "No file provided.";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void I_can_check_a_partial_file() throws Exception {

        mockMvcHeadersForFile(
                request(HEAD, "/file")
                        .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                        .header("Range", "bytes=0-10"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes 0-10/%d", FILE_ONE.getSize())))
                .andExpect(content().contentType(FILE_ONE.getMediaType()))
                .andExpect(content().string(""));
    }

    @Test
    public void I_can_request_a_partial_file() throws Exception {

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                        .header("Range", "bytes=6-12"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes 6-12/%d", FILE_ONE.getSize())))
                .andExpect(content().contentType(FILE_ONE.getMediaType()))
                .andExpect(content().string("ile one"));
    }

    @Test
    public void I_can_request_a_partial_file_without_a_path() throws Exception {

        mockMvc.perform(get("/file").header("Range", "bytes=6-12"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(NO_FILE_ERROR_MESSAGE));
    }

    @Test
    public void I_can_request_the_last_partial_file_with_an_end_that_is_too_large() throws Exception {

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                        .header("Range", "bytes=6-1000000"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes 6-13/%d", FILE_ONE.getSize())))
                .andExpect(content().contentType(FILE_ONE.getMediaType()))
                .andExpect(content().string("ile one."));
    }

    @Test
    public void I_can_request_all_of_the_file_with_a_zero_start_and_an_end_that_is_too_large() throws Exception {

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                        .header("Range", "bytes=0-1000000"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes 0-13/%d", FILE_ONE.getSize())))
                .andExpect(content().contentType(FILE_ONE.getMediaType()))
                .andExpect(content().string(FILE_ONE.getContent()));
    }

    @Test
    public void I_can_request_a_partial_file_with_a_large_range() throws Exception {

        final int start = 10000;
        final int end = 100500;

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(FILE, new JavaStreamFile(FILE_EIGHT.getAbsolutePath()))
                        .header("Range", format("bytes=%d-%d", start, end)),
                FILE_EIGHT
        ).andExpect(status().isPartialContent())
                .andExpect(header().string("Content-Range", format("bytes %d-%d/%d", start, end, FILE_EIGHT.getSize())))
                .andExpect(content().contentType(FILE_EIGHT.getMediaType()))
                .andExpect(content().bytes(Arrays.copyOfRange(FILE_EIGHT.getContent(), start, end + 1)));
    }

    @Test
    public void I_can_check_a_partial_file_with_a_range_value_with_a_start_value_greater_than_the_end_value()
            throws Exception {

        mockMvcHeadersForFile(
                request(HEAD, "/file")
                        .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                        .header("Range", "bytes=10-0"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().doesNotExist("Content-Range"))
                .andExpect(content().contentType(FILE_ONE.getMediaType()))
                .andExpect(content().string(""));
    }

    @Test
    public void I_can_request_a_partial_file_with_a_range_value_with_a_start_value_greater_than_the_end_value()
            throws Exception {

        mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                        .header("Range", "bytes=10-0"),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(header().doesNotExist("Content-Range"))
                .andExpect(content().contentType(FILE_ONE.getMediaType()))
                .andExpect(content().string(FILE_ONE.getContent()));
    }

    @Test
    public void I_cannot_request_a_partial_file_with_no_ranges() throws Exception {

        mockMvc.perform(get("/file")
                .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                .header("Range", "bytes=")
        ).andExpect(status().isRequestedRangeNotSatisfiable())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(format(
                        "The supplied Range header is either malformed or out of bounds. Range: bytes=, File Size: %d",
                        FILE_ONE.getSize())));
    }

    @Test
    public void I_cannot_request_a_partial_file_with_an_invalid_range() throws Exception {

        mockMvc.perform(get("/file")
                .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                .header("Range", "bytes=1000-1001")
        ).andExpect(status().isRequestedRangeNotSatisfiable())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(format(
                        "The supplied Range header is either malformed or out of bounds. Range: 1000-1001, File Size: %d",
                        FILE_ONE.getSize())));
    }

    @Test
    public void I_can_request_a_partial_file_with_multiple_ranges() throws Exception {

        final String boundaryPrefix = "MULTI_PART_BOUNDARY";

        final int start1 = 0;
        final int end1 = 5;

        final int start2 = 7;
        final int end2 = 10;

        final int start3 = 12;
        final int end3 = 13;

        final MvcResult result = mockMvcHeadersForFile(
                get("/file")
                        .requestAttr(FILE, new JavaStreamFile(FILE_ONE.getAbsolutePath()))
                        .header("Range", format("bytes=%d-%d,%d-%d,%d-%d", start1, end1, start2, end2, start3, end3)),
                FILE_ONE
        ).andExpect(status().isPartialContent())
                .andExpect(content().contentTypeCompatibleWith("multipart/byteranges"))
                .andExpect(header().string("Content-Type", containsString(format("boundary=%s", boundaryPrefix))))
                .andReturn();

        final String boundary = extractBoundary(result);

        final String content = buildContent(
                FILE_ONE,
                FILE_ONE.getMimeType(),
                Arrays.<Entry<Integer, Integer>>asList(
                        new SimpleEntry<>(start1, end1),
                        new SimpleEntry<>(start2, end2),
                        new SimpleEntry<>(start3, end3)
                ),
                boundary);

        assertEquals("the response body should contain multiparts", content, result.getResponse().getContentAsString());
    }

    private ResultActions mockMvcHeadersForFile(MockHttpServletRequestBuilder requestBuilder, TestFile file)
            throws Exception {

        return Controllers.mockMvcHeadersForFile(mockMvc, requestBuilder, file);
    }

    private static String extractBoundary(MvcResult result) {

        final String contentType = result.getResponse().getContentType();

        return contentType.replace("multipart/byteranges;boundary=", "");
    }
}
