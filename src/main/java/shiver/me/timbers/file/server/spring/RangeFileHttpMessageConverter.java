package shiver.me.timbers.file.server.spring;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import shiver.me.timbers.file.server.Range;
import shiver.me.timbers.file.server.RangeFile;

import java.io.IOException;

import static java.lang.String.format;
import static shiver.me.timbers.file.server.spring.Requests.CONTENT_RANGE;

/**
 * This http message converter converts a {@link shiver.me.timbers.file.server.RangeFile} type into a valid response while also adding all the
 * required headers.
 *
 * @author Karl Bennett
 */
public class RangeFileHttpMessageConverter extends FileHttpMessageConverter<RangeFile> {

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return RangeFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void write(RangeFile file, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        final HttpHeaders headers = outputMessage.getHeaders();

        addContentRange(headers, file);

        super.write(file, contentType, outputMessage);
    }

    private static void addContentRange(HttpHeaders headers, RangeFile file) {

        final Range range = file.getRange();

        if (range.isValid()) {

            headers.set(CONTENT_RANGE, format("bytes %s/%d", file.getRange(), file.getSize()));
        }
    }
}
