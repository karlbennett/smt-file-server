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
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof File)) return false;

        if (!super.equals(o)) return false;

        final File file = (File) o;

        if (!extension.equals(file.getExtension())) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + extension.hashCode();

        return result;
    }
}
