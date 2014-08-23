package shiver.me.timbers.file.server;

import shiver.me.timbers.file.io.AbstractFileCreator;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.JavaFile;

public class RangeFileCreator extends AbstractFileCreator {

    private long start;
    private long end;
    private boolean useDefaults;

    public RangeFileCreator() {
        this.start = 0;
        this.end = 0;
        this.useDefaults = true;
    }

    public void setRange(long start, long end) {
        this.start = start;
        this.end = end;
        this.useDefaults = false;
    }

    @Override
    public File create(String path) {

        final File file = new JavaFile(path);

        final long start = useDefaults ? 0 : this.start;
        final long end = useDefaults ? file.getSize() : this.end;

        return create(file, start, end);
    }

    protected File create(File file, long start, long end) {

        return new RangeFile(file, new Range(start, end, file.getSize()));
    }

    @SuppressWarnings("unchecked")
    public <R extends File> Class<R> type() {

        return (Class<R>) RangeFile.class;
    }
}
