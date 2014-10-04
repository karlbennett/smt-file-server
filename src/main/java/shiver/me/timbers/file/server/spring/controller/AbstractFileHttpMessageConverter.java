package shiver.me.timbers.file.server.spring.controller;

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
import static shiver.me.timbers.file.server.spring.controller.Responses.addFileHeaders;

/**
 * This class contains the common logic that is used across all {@link File} http message converters.
 *
 * @author Karl Bennett
 */
public abstract class AbstractFileHttpMessageConverter<F extends File> implements HttpMessageConverter<F> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {

        return singletonList(MediaType.ALL);
    }

    @Override
    public F read(Class<? extends F> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {

        throw new UnsupportedOperationException("This is not required.");
    }

    @Override
    public void write(F file, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        final HttpHeaders headers = outputMessage.getHeaders();

        addFileHeaders(headers, file);

        write(file, outputMessage);
    }

    public abstract void write(F file, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException;
}
