package shiver.me.timbers.file.server;

import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.JavaStreamFile;
import shiver.me.timbers.file.io.TestFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.Entry;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RangeFiles {

    public static RangeFile buildRangeFile(TestFile file) {

        return buildRangeFile(file, 0, file.getSize());
    }

    public static RangeFile buildRangeFile(TestFile file, long start, long end) {

        final String path = file.getAbsolutePath();
        final long size = file.getSize();

        return new RangeFile(new JavaStreamFile(path), new Range(start, end, size));
    }

    public static RangesFile buildRangesFile(TestFile file) {

        return buildRangesFile(file, 0, file.getSize());
    }

    public static RangesFile buildRangesFile(TestFile file, long start, long end) {

        return buildRangesFile(file, buildRanges(file, start, end));
    }

    public static RangesFile buildRangesFile(TestFile file, Ranges ranges) {

        final String path = file.getAbsolutePath();

        return new RangesFile(new JavaStreamFile(path), ranges);
    }

    public static Ranges buildRanges(File file, long start, long end) {

        final long fileSize = file.getSize();

        return buildRanges(start, end, fileSize);
    }

    public static Ranges buildRanges(long start, long end, long fileSize) {

        return new Ranges(fileSize, new Range(start, end, fileSize));
    }

    public static Ranges buildRangesWithMocks(File file) {

        return buildRanges(file, mockRange(file, 0, 5), mockRange(file, 7, 10), mockRange(file, 12, 14));
    }

    public static Ranges buildRanges(File file, Range... ranges) {

        return new Ranges(file.getSize(), ranges);
    }

    public static Ranges buildRanges(File file, List<Entry<Integer, Integer>> ranges) {

        final List<Range> rangeList = new ArrayList<>(ranges.size());

        for (Map.Entry<Integer, Integer> range : ranges) {

            rangeList.add(new Range(range.getKey(), range.getValue(), file.getSize()));
        }

        return new Ranges(file.getSize(), rangeList.toArray(new Range[rangeList.size()]));
    }

    public static Range mockRange(File file, long start, long end) {

        final Range range = mock(Range.class);
        when(range.getFileSize()).thenReturn(file.getSize());
        when(range.getStart()).thenReturn(start);
        when(range.getEnd()).thenReturn(end);
        when(range.isValid()).thenReturn(true);

        return range;
    }
}
