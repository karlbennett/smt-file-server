package shiver.me.timbers.file.server;

import shiver.me.timbers.file.io.DefaultFileContentGetter;
import shiver.me.timbers.file.io.TestFile;

public class RangeFileContentGetter extends DefaultFileContentGetter<String> {

    private final int start;
    private final int end;

    public RangeFileContentGetter(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String get(TestFile<String> target) {

        final String content = super.get(target);

        return content.substring(start, end + 1);
    }
}
