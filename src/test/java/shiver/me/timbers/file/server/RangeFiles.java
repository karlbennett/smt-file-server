package shiver.me.timbers.file.server;

import shiver.me.timbers.file.io.JavaFile;
import shiver.me.timbers.file.io.TestFile;

public class RangeFiles {

    public static RangeFile buildRangeFile(TestFile file) {

        return buildRangeFile(file, 0, file.getSize());
    }

    public static RangeFile buildRangeFile(TestFile file, long start, long end) {

        final String path = file.getAbsolutePath();
        final long size = file.getSize();

        return new RangeFile(new JavaFile(path), new Range(start, end, size));
    }
}
