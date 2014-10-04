package shiver.me.timbers.file.server.spring.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import shiver.me.timbers.file.server.RangeFile;
import shiver.me.timbers.file.server.RangesFile;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.String.format;
import static java.util.Collections.singletonMap;

/**
 * This http message converter converts a {@link shiver.me.timbers.file.server.RangesFile} type into a valid response
 * while also adding all the required headers.
 *
 * @author Karl Bennett
 */
public class RangesFileHttpMessageConverter extends AbstractFileHttpMessageConverter<RangesFile> {

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {

        return RangesFile.class.isAssignableFrom(clazz);
    }

    @Override
    public void write(RangesFile rangesFile, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        checkRangeFileIsValid(rangesFile);

        final HttpHeaders headers = outputMessage.getHeaders();

        // Must get the responses mime type before any modifications are made to the headers and the content type is
        // overwritten.
        final MediaType contentType = headers.getContentType();

        final String boundary = generateBoundary();

        addMultiPartByteRanges(headers, boundary);

        final OutputStream body = outputMessage.getBody();

        for (RangeFile file : rangesFile) {

            writeBoundary(boundary, body);
            writeContentType(contentType, body);
            writeContentRange(body, file);
            writeNewLine(body);
            IOUtils.copy(file.getInputStream(), body);
        }

        writeBoundaryEnd(boundary, body);
    }

    private static void checkRangeFileIsValid(RangesFile file) {

        if (!file.getRanges().isValid()) {
            throw new IllegalArgumentException("The ranges within the range file must be valid.");
        }
    }

    private static String generateBoundary() {

        final Random random = ThreadLocalRandom.current();

        final BigInteger bigInteger = new BigInteger(130, random);

        return format("MULTI_PART_BOUNDARY_%s", bigInteger.toString(32));
    }

    private static void addMultiPartByteRanges(HttpHeaders headers, String boundary) {

        final MediaType mediaType = new MediaType("multipart", "byteranges", singletonMap("boundary", boundary));

        headers.setContentType(mediaType);
    }

    private static void writeBoundary(String boundary, OutputStream body) throws IOException {

        writeNewLine(body);
        IOUtils.write("--", body);
        IOUtils.write(boundary, body);
        writeNewLine(body);
    }

    private static void writeContentRange(OutputStream body, RangeFile file) throws IOException {

        IOUtils.write("Content-Range: bytes ", body);
        IOUtils.write(file.getRange().toString(), body);
        IOUtils.write("/", body);
        IOUtils.write(String.valueOf(file.getSize()), body);
        writeNewLine(body);
    }

    private static void writeContentType(MediaType contentType, OutputStream body) throws IOException {

        IOUtils.write("Content-Type: ", body);
        IOUtils.write(contentType.toString(), body);
        writeNewLine(body);
    }

    private static void writeNewLine(OutputStream body) throws IOException {

        IOUtils.write("\n", body);
    }

    private static void writeBoundaryEnd(String boundary, OutputStream body) throws IOException {

        writeNewLine(body);
        IOUtils.write("--", body);
        IOUtils.write(boundary, body);
        IOUtils.write("--", body);
        writeNewLine(body);
    }
}
