package shiver.me.timbers.file.io;

public interface TestFileSystemElement extends FileSystemElement {

    public String getPath();

    public String getAbsolutePath();

    public java.io.File getFile();
}
