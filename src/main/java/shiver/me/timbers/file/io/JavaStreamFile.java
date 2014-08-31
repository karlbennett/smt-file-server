package shiver.me.timbers.file.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static java.lang.String.format;

public class JavaStreamFile extends JavaFile implements StreamFile {

    private final java.io.File file;

    public JavaStreamFile(String path) {
        this(new java.io.File(path));
    }

    public JavaStreamFile(String root, String path) {
        this(new java.io.File(root, path));
    }

    public JavaStreamFile(java.io.File file) {
        super(file);

        this.file = file;
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
    public InputStream getInputStream() {

        return getInputStream(file);
    }
}
