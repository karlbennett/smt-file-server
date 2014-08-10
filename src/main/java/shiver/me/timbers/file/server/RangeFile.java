package shiver.me.timbers.file.server;

import org.apache.commons.io.input.BoundedInputStream;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.InvalidPathException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * The returned input stream within this file will only allow streaming of the file part represented by the
 * {@link Range} used to construct it.
 *
 * @author Karl Bennett
 */
public class RangeFile implements File {

    private final File file;
    private final Range range;

    public RangeFile(File file, Range range) {

        if (null == file) {
            throw new NullPointerException("A range file must not have a null file.");
        }

        if (null == range) {
            throw new NullPointerException("A range file must not have a null range.");
        }

        if (file.getSize() != range.getFileSize()) {
            throw new RequestedRangeNotSatisfiableException(range.toString(), file.getSize());
        }

        this.file = file;
        this.range = range;
    }

    @Override
    public String getExtension() {
        return file.getExtension();
    }

    @Override
    public long getSize() {
        return file.getSize();
    }

    @Override
    public InputStream getInputStream() {

        if (rangeIsInvalid()) {
            return file.getInputStream();
        }

        final InputStream input = file.getInputStream();

        skip(input, range.getStart());

        return new BoundedInputStream(input, range.getSize());
    }

    private boolean rangeIsInvalid() {
        return !range.isValid();
    }

    private static void skip(InputStream input, long bytes) {

        try {

            input.skip(bytes);

        } catch (IOException e) {

            throw new InvalidPathException(e);
        }
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public Date getModified() {
        return file.getModified();
    }

    public Range getRange() {
        return range;
    }


    @Override
    public String toString() {
        return file.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof RangeFile)) {
            return false;
        }

        RangeFile rangeFile = (RangeFile) o;

        if (!file.equals(rangeFile.file)) {
            return false;
        }
        if (!range.equals(rangeFile.range)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = file.hashCode();
        result = 31 * result + range.hashCode();

        return result;
    }
}
