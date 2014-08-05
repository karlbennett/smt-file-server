package shiver.me.timbers.file.server;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static shiver.me.timbers.file.server.Requests.addFileHeaders;

/**
 * This http message converter converts a file type into a valid response while also adding all the required headers.
 *
 * @author Karl Bennett
 */
public class FileHttpMessageConverter extends AbstractHttpMessageConverter<File> {

    public FileHttpMessageConverter() {
        super(MediaType.ALL);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return File.class.isAssignableFrom(clazz);
    }

    @Override
    protected File readInternal(Class<? extends File> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException("Boy I hope this isn't called.");
    }

    @Override
    protected void writeInternal(File file, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        final HttpHeaders headers = outputMessage.getHeaders();

        addFileHeaders(headers, file);

        IOUtils.copy(new FileInputStream(file), outputMessage.getBody());
    }
}
