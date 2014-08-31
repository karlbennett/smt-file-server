package shiver.me.timbers.file.server;

import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.StreamFile;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This file contains multiple {@link RangeFile}s that all relate to the same {@link File}.
 *
 * @author Karl Bennett
 */
public class RangesFile extends AbstractList<RangeFile> implements File {

    private final File file;
    private final Ranges ranges;
    private final List<RangeFile> rangeFiles;

    public RangesFile(StreamFile file, Ranges ranges) {

        if (null == file) {
            throw new NullPointerException("A ranges file must not have a null file.");
        }

        if (null == ranges) {
            throw new NullPointerException("A ranges file must not have null ranges.");
        }

        if (0 >= ranges.size()) {
            throw new IllegalArgumentException("A ranges file must contain some ranges.");
        }

        if (!ranges.isValid()) {
            throw new IllegalArgumentException("A ranges file must contain valid ranges.");
        }

        this.file = file;
        this.ranges = ranges;

        this.rangeFiles = new ArrayList<>(ranges.size());

        for (Range range : this.ranges) {

            this.rangeFiles.add(new RangeFile(file, range));
        }
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
    public String getMimeType() {
        return file.getMimeType();
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public Date getModified() {
        return file.getModified();
    }

    public Ranges getRanges() {
        return ranges;
    }

    @Override
    public RangeFile get(int index) {
        return rangeFiles.get(index);
    }

    @Override
    public int size() {
        return rangeFiles.size();
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
        if (!(o instanceof RangesFile)) {
            return false;
        }

        RangesFile rangeFile = (RangesFile) o;

        if (!file.equals(rangeFile.file)) {
            return false;
        }
        if (!rangeFiles.equals(rangeFile.rangeFiles)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = file.hashCode();
        result = 31 * result + rangeFiles.hashCode();

        return result;
    }
}
