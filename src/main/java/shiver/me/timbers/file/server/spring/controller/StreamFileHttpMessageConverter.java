package shiver.me.timbers.file.server.spring.controller;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import shiver.me.timbers.file.io.StreamFile;

import java.io.IOException;

import static org.apache.commons.io.IOUtils.copy;

/**
 * This http message converter converts a {@link StreamFile} type into a valid response while also adding all the
 * required headers.
 *
 * @author Karl Bennett
 */
public class StreamFileHttpMessageConverter<F extends StreamFile> extends AbstractFileHttpMessageConverter<F> {

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return StreamFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void write(F file, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        copy(file.getInputStream(), outputMessage.getBody());
    }
}
