package shiver.me.timbers.file.server.spring.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import shiver.me.timbers.file.server.RangeFile;

import java.io.IOException;

import static java.lang.String.format;
import static shiver.me.timbers.file.server.spring.controller.Responses.CONTENT_RANGE;

/**
 * This http message converter converts a {@link shiver.me.timbers.file.server.RangeFile} type into a valid response while also adding all the
 * required headers.
 *
 * @author Karl Bennett
 */
public class RangeFileHttpMessageConverter extends StreamFileHttpMessageConverter<RangeFile> {

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return RangeFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void write(RangeFile file, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        final HttpHeaders headers = outputMessage.getHeaders();

        checkRangeFileIsValid(file);

        addContentRange(headers, file);

        super.write(file, outputMessage);
    }

    private static void checkRangeFileIsValid(RangeFile file) {
        if (!file.getRange().isValid()) {
            throw new IllegalArgumentException("The within the range file must be valid.");
        }
    }

    private static void addContentRange(HttpHeaders headers, RangeFile file) {

        headers.set(CONTENT_RANGE, format("bytes %s/%d", file.getRange(), file.getSize()));
    }
}
