package shiver.me.timbers.file.server.spring;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import shiver.me.timbers.file.io.TestFile;

import static java.lang.String.format;
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
}
