package shiver.me.timbers.file.io;

import shiver.me.timbers.Getter;

public class DefaultFileContentGetter<R> implements Getter<R, TestFile<R>> {

    @Override
    public R get(TestFile<R> target) {
        return target.getContent();
    }
}
