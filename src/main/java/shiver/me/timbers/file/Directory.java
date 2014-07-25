package shiver.me.timbers.file;

import java.util.List;

/**
 * A {@link FileSystemElement} that can contain other {@link Directory Directories}s and {@link File}s.
 *
 * @author Karl Bennett
 */
public interface Directory extends FileSystemElement {

    /**
     * @return a list of {@code FileSystemElement} instances so that the directory lookup doesn't recurs through the
     * entire directory tree.
     */
    public List<FileSystemElement> getDirectories();

    public List<File> getFiles();
}
