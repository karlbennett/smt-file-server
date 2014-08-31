package shiver.me.timbers.file.io;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaFile extends JavaFileSystemElement implements File {

    private final String extension;
    private final long size;
    private final String mimeType;

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

        this.extension = deriveExtension(canonicalFile);
        this.size = file.length();
        this.mimeType = inspectMediaType(canonicalFile, this.extension);
    }

    private static String deriveExtension(java.io.File canonicalFile) {

        final String extension = FilenameUtils.getExtension(canonicalFile.getName());

        return shouldHaveExtension(canonicalFile, extension) ? extension : "";
    }


    private static boolean shouldHaveExtension(java.io.File file, String extension) {

        final String fileNameMinusExtension = file.getName().replace(extension, "");

        // Handles when a filename starts with a '.' and has no there extension e.g. .gitignore
        if (".".equals(fileNameMinusExtension)) {
            return false;
        }

        return true;
    }

    private static String inspectMediaType(java.io.File file, String extension) {

        final String mimeType = probeMimeType(file);

        // It seem that at the moment Files.probeContentType(Paths) returns a mime type of "text/plain" for JSON files.
        if (isJsonFile(mimeType, extension)) {

            return "application/json";
        }

        return mimeType;
    }

    static String probeMimeType(java.io.File file) {

        try {

            return Files.probeContentType(Paths.get(file.getCanonicalPath()));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    private static boolean isJsonFile(String mimeType, String extension) {

        return "text/plain".equals(mimeType) && "json".equalsIgnoreCase(extension);
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public String getMimeType() {
        return mimeType;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof File)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        final File that = (File) o;

        if (size != that.getSize()) {
            return false;
        }

        if (!extension.equals(that.getExtension())) {
            return false;
        }

        if (!mimeType.equals(that.getMimeType())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        int result = super.hashCode();
        result = 31 * result + extension.hashCode();
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + mimeType.hashCode();

        return result;
    }
}
