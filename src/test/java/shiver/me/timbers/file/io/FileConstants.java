package shiver.me.timbers.file.io;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.http.MediaType.parseMediaType;
import static shiver.me.timbers.Constants.TEST_PROPERTIES;

public class FileConstants {

    public static final TestFile<String> TEST_PROPERTIES_FILE = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.testProperties"), TEXT_PLAIN_VALUE, "properties");

    public static final TestFile<String> FILE_ONE = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileOne"), TEXT_PLAIN_VALUE, "txt");

    public static final TestFile<String> FILE_TWO = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileTwo"), TEXT_PLAIN_VALUE, "");

    public static final TestFile<String> FILE_THREE = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileThree"), TEXT_PLAIN_VALUE, "txt");

    public static final TestFile<String> FILE_FOUR = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileFour"), TEXT_PLAIN_VALUE, "txt");

    public static final TestFile<String> FILE_FIVE = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileFive"), APPLICATION_JSON_VALUE, "json");

    public static final TestFile<String> FILE_SIX = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileSix"), APPLICATION_XML_VALUE, "xml");

    public static final TestFile<byte[]> FILE_SEVEN = new BinaryFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileSeven"), IMAGE_PNG_VALUE, "png");

    public static final TestFile<byte[]> FILE_EIGHT = new BinaryFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileEight"), "video/mp4", "mp4");


    private static abstract class AbstractTestFile<C> extends AbstractTestFileSystemElement implements TestFile<C> {

        private final String extension;
        private final long size;
        private final String mimeType;

        protected AbstractTestFile(String absolutePath, String mimeType, String extension) {
            super(absolutePath);

            this.extension = extension;
            this.mimeType = mimeType;
            this.size = fileSize(absolutePath);
        }

        private long fileSize(String absolutePath) {

            try {

                return Files.size(Paths.get(absolutePath));

            } catch (IOException e) {

                throw new RuntimeException(e);
            }
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
        public MediaType getMediaType() {
            return parseMediaType(mimeType);
        }

        @Override
        public InputStream getInputStream() {
            throw new UnsupportedOperationException("A test file does not have an input stream.");
        }
    }

    private static class TextFile extends AbstractTestFile<String> {

        private final String content;

        private TextFile(String absolutePath, String mimeType, String extension) {
            super(absolutePath, mimeType, extension);

            this.content = readFileToString(absolutePath);
        }

        private static String readFileToString(String path) {

            try {

                return FileUtils.readFileToString(new java.io.File(path));

            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }

        @Override
        public String getContent() {
            return content;
        }
    }

    private static class BinaryFile extends AbstractTestFile<byte[]> {

        private final byte[] content;

        private BinaryFile(String absolutePath, String mimeType, String extension) {
            super(absolutePath, mimeType, extension);

            this.content = readFileToByteArray(absolutePath);
        }

        private static byte[] readFileToByteArray(String path) {

            try {

                return FileUtils.readFileToByteArray(new File(path));

            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }

        @Override
        public byte[] getContent() {
            return content;
        }
    }
}
