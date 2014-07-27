package shiver.me.timbers.file.io;

import java.util.ArrayList;
import java.util.List;

public class JavaDirectory extends JavaFileSystemElement implements Directory {

    private final List<FileSystemElement> directories;
    private final List<File> files;

    public JavaDirectory(String path) {
        this(new java.io.File(path));
    }

    public JavaDirectory(String root, String path) {
        this(new java.io.File(root, path));
    }

    public JavaDirectory(java.io.File file) {
        super(file);

        final java.io.File canonicalFile = getCanonicalFile(file);

        if (canonicalFile.isFile()) {
            throw new InvalidPathException("The supplied path is to a file not a directory.");
        }

        final java.io.File[] files = canonicalFile.listFiles();

        if (null == files) {
            throw new InvalidPathException("Could not list the contents of the directory.");
        }

        this.directories = new ArrayList<>();
        this.files = new ArrayList<>();

        populateLists(files);
    }

    private void populateLists(java.io.File[] files) {

        for (java.io.File file : files) {

            if (file.isDirectory()) {

                this.directories.add(new JavaFileSystemElement(getCanonicalFile(file).getPath()));
            }

            if (file.isFile()) {

                this.files.add(new JavaFile(getCanonicalFile(file).getPath()));
            }
        }
    }

    @Override
    public List<FileSystemElement> getDirectories() {
        return directories;
    }

    @Override
    public List<File> getFiles() {
        return files;
    }
}
