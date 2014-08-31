package shiver.me.timbers.file.io;

import java.util.Date;

import static java.lang.String.format;

public class JavaFileSystemElement implements FileSystemElement {

    private final String name;
    private final Date modified;

    public JavaFileSystemElement(String path) {
        this(new java.io.File(path));
    }

    public JavaFileSystemElement(String root, String path) {
        this(new java.io.File(root, path));
    }

    public JavaFileSystemElement(java.io.File file) {

        if (!file.exists()) {
            throw new InvalidPathException(format("Nothing exists at %s", file.getPath()));
        }

        name = file.getName();
        modified = new Date(file.lastModified());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getModified() {
        return modified;
    }

    @Override
    public String toString() {
        return format("{ \"name\" : \"%s\", \"modified\" : \"%tc\" }", name, modified);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof FileSystemElement)) {
            return false;
        }

        final FileSystemElement that = (FileSystemElement) o;

        if (!modified.equals(that.getModified())) {
            return false;
        }

        if (!name.equals(that.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = name.hashCode();
        result = 31 * result + modified.hashCode();

        return result;
    }
}
