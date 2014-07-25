package shiver.me.timbers.file;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.Date;

import static java.lang.String.format;

public class JavaFileSystemElement implements FileSystemElement {

    private final String name;
    private final String extension;
    private final Date created;

    public JavaFileSystemElement(String path) {
        this(new java.io.File(path));
    }

    public JavaFileSystemElement(java.io.File file) {

        final java.io.File canonicalFile = getCanonicalFile(file);

        if (!canonicalFile.exists()) {
            throw new InvalidPathException(format("Nothing exists at %s", file.getPath()));
        }

        name = canonicalFile.getName();
        extension = FilenameUtils.getExtension(name);
        created = new Date(canonicalFile.lastModified());
    }

    static java.io.File getCanonicalFile(java.io.File file) {

        try {

            return file.getCanonicalFile();

        } catch (IOException e) {

            throw new InvalidPathException(format("Could not access file system at %s", file.getPath()), e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public Date getModified() {
        return created;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof JavaFileSystemElement)) return false;

        JavaFileSystemElement that = (JavaFileSystemElement) o;

        if (!created.equals(that.created)) return false;
        if (!extension.equals(that.extension)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = name.hashCode();
        result = 31 * result + extension.hashCode();
        result = 31 * result + created.hashCode();

        return result;
    }
}
