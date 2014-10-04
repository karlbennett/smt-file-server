package shiver.me.timbers.file.server.spring.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.TestFile;

import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static java.util.Map.Entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static shiver.me.timbers.file.server.ServerConstants.dateFormat;

public class Controllers {

    public static ResultActions mockMvcHeadersForFile(MockMvc mockMvc, MockHttpServletRequestBuilder requestBuilder,
                                                      TestFile file) throws Exception {

        return mockMvc.perform(requestBuilder)
                .andExpect(header().string("Accept-Ranges", "bytes"))
                .andExpect(header().string("ETag", format("\"%s_%d_%d\"", file.getName(), file.getSize(),
                        file.getModified().getTime())))
                .andExpect(header().string("Last-Modified", dateFormat().format(file.getModified())));
    }

    public static String buildContent(TestFile<String> file, String mediaType, List<Entry<Integer, Integer>> ranges,
                                      String boundary) {

        final StringBuilder content = new StringBuilder();

        for (Entry<Integer, Integer> range : ranges) {

            content.append("\n");
            content.append("--").append(boundary).append("\n");
            content.append("Content-Type: ").append(mediaType).append("\n");
            content.append("Content-Range: bytes ")
                    .append(range.getKey()).append("-").append(range.getValue())
                    .append("/").append(file.getSize()).append("\n");
            content.append("\n");
            content.append(file.getContent().substring(range.getKey(), range.getValue() + 1));
        }

        content.append("\n");
        content.append("--").append(boundary).append("--").append("\n");

        return content.toString();
    }

    public static <F extends File> F mockFile(Class<F> type) {

        final F file = mock(type);
        when(file.getMimeType()).thenReturn(TEXT_PLAIN_VALUE);
        when(file.getModified()).thenReturn(new Date());

        return file;
    }

    public static HttpOutputMessage mockHttpOutputMessage() {
        final HttpHeaders headers = mock(HttpHeaders.class);

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);

        return message;
    }
}
