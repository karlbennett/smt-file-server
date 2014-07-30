package shiver.me.timbers.file.io;

public interface TestFile<C> extends TestFileSystemElement, File {

    public C getContent();
}
