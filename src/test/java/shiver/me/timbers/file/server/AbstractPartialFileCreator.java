package shiver.me.timbers.file.server;

import shiver.me.timbers.file.io.AbstractFileCreator;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.JavaStreamFile;
import shiver.me.timbers.file.io.StreamFile;

public abstract class AbstractPartialFileCreator<F extends File> extends AbstractFileCreator<F> {

    private long start;
    private long end;
    private boolean useDefaults;

    public AbstractPartialFileCreator() {
        this.start = 0;
        this.end = 0;
        this.useDefaults = true;
    }

    public AbstractPartialFileCreator(long start, long end) {
        setRange(start, end);
    }

    public void setRange(long start, long end) {
        this.start = start;
        this.end = end;
        this.useDefaults = false;
    }

    @Override
    public F create(String path) {

        final StreamFile file = new JavaStreamFile(path);

        final long start = useDefaults ? 0 : this.start;
        final long end = useDefaults ? file.getSize() : this.end;

        return create(file, start, end);
    }

    public abstract F create(StreamFile file, long start, long end);
}
