package shiver.me.timbers.file.io;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static java.lang.String.format;

public class JavaFile extends JavaFileSystemElement implements File {

    private final java.io.File file;

    public JavaFile(String path) {
        this(new java.io.File(path));
    }

    public JavaFile(java.io.File file) {
        super(file);

        final java.io.File canonicalFile = getCanonicalFile(file);

        if (canonicalFile.isDirectory()) {
            throw new InvalidPathException("The supplied path is to a directory not a file.");
        }

        this.file = canonicalFile;
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
}
