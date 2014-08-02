package shiver.me.timbers.file.io;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.io.FilenameUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static java.lang.String.format;

public class JavaFile extends JavaFileSystemElement implements File {

    private final java.io.File file;
    private final String extension;
    private final long size;

    public JavaFile(String path) {
        this(new java.io.File(path));
    }

    public JavaFile(String root, String path) {
        this(new java.io.File(root, path));
    }

    public JavaFile(java.io.File file) {
        super(file);

        final java.io.File canonicalFile = getCanonicalFile(file);

        if (canonicalFile.isDirectory()) {
            throw new InvalidPathException("The supplied path is to a directory not a file.");
        }

        this.file = canonicalFile;
        this.extension = shouldHaveExtension(canonicalFile) ? FilenameUtils.getExtension(canonicalFile.getName()) : "";
        this.size = file.length();
    }


    private static boolean shouldHaveExtension(java.io.File file) {

        // Handles when a filename starts with a '.' and has no there extension e.g. .gitignore
        if (".".equals(file.getName().replace(FilenameUtils.getExtension(file.getName()), ""))) {
            return false;
        }

        return true;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public long getSize() {
        return size;
    }

    @JsonIgnore
    @Override
    public InputStream getInputStream() {

        return getInputStream(file);
    }

    static InputStream getInputStream(java.io.File file) {

        try {

            return new FileInputStream(file);

        } catch (FileNotFoundException e) {

            throw new InvalidPathException(
                    format("Could not get the input stream for a file with path: %s", file.getPath()), e);
        }
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (!(object instanceof File)) {
            return false;
        }
        if (!super.equals(object)) {
            return false;
        }

        File file = (File) object;

        if (size != file.getSize()) {
            return false;
        }
        if (!extension.equals(file.getExtension())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + extension.hashCode();
        result = 31 * result + (int) (size ^ (size >>> 32));

        return result;
    }
}
