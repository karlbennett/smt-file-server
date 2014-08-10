package shiver.me.timbers.file.server;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import shiver.me.timbers.file.io.File;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.apache.commons.io.IOUtils.copy;
import static shiver.me.timbers.file.server.Requests.addFileHeaders;

/**
 * This http message converter converts a {@link File} type into a valid response while also adding all the required
 * headers.
 *
 * @author Karl Bennett
 */
public class FileHttpMessageConverter<F extends File> implements HttpMessageConverter<F> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return File.class.isAssignableFrom(clazz);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return singletonList(MediaType.ALL);
    }

    @Override
    public F read(Class<? extends F> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException(" This is not required.");
    }

    @Override
    public void write(F file, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        final HttpHeaders headers = outputMessage.getHeaders();

        addFileHeaders(headers, file);

        copy(file.getInputStream(), outputMessage.getBody());
    }
}
