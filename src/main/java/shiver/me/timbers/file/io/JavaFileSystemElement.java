package shiver.me.timbers.file.io;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.util.Date;

import static java.lang.String.format;

public class JavaFileSystemElement implements FileSystemElement {

    private final String name;
    private final String extension;
    private final Date modified;

    public JavaFileSystemElement(String path) {
        this(new java.io.File(path));
    }

    public JavaFileSystemElement(java.io.File file) {

        final java.io.File canonicalFile = getCanonicalFile(file);

        if (!canonicalFile.exists()) {
            throw new InvalidPathException(format("Nothing exists at %s", file.getPath()));
        }

        name = canonicalFile.getName();
        // Only files should have extensions.
        extension = shouldHaveExtension(canonicalFile) ? FilenameUtils.getExtension(name) : "";
        modified = new Date(canonicalFile.lastModified());
    }

    static java.io.File getCanonicalFile(java.io.File file) {

        try {

            return file.getCanonicalFile();

        } catch (IOException e) {

            throw new InvalidPathException(format("Could not access file system at %s", file.getPath()), e);
        }
    }

    private static boolean shouldHaveExtension(java.io.File file) {

        // Directories should not have an extension.
        if (file.isDirectory()) {
            return false;
        }

        if (null == file.getName()) {
            return false;
        }

        // Handles when a filename starts with a '.' and has no there extension e.g. .gitignore
        if (".".equals(file.getName().replace(FilenameUtils.getExtension(file.getName()), ""))) {
            return false;
        }

        return true;
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
        return modified;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof FileSystemElement)) return false;

        FileSystemElement that = (FileSystemElement) o;

        if (!modified.equals(that.getModified())) return false;
        if (!extension.equals(that.getExtension())) return false;
        if (!name.equals(that.getName())) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = name.hashCode();
        result = 31 * result + extension.hashCode();
        result = 31 * result + modified.hashCode();

        return result;
    }
}
