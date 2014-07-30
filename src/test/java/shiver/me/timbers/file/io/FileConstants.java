package shiver.me.timbers.file.io;

import java.io.InputStream;

import static shiver.me.timbers.Constants.TEST_PROPERTIES;
import static shiver.me.timbers.Utils.readFileToByteArray;
import static shiver.me.timbers.Utils.readFileToString;

public class FileConstants {

    public static final TestFile<String> TEST_PROPERTIES_FILE = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.testProperties"), "properties");

    public static final TestFile<String> FILE_ONE = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileOne"), "txt");

    public static final TestFile<String> FILE_TWO = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileTwo"), "");

    public static final TestFile<String> FILE_THREE = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileThree"), "txt");

    public static final TestFile<String> FILE_FOUR = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileFour"), "txt");

    public static final TestFile<String> FILE_FIVE = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileFive"), "json");

    public static final TestFile<String> FILE_SIX = new TextFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileSix"), "xml");

    public static final TestFile<byte[]> FILE_SEVEN = new BinaryFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileSeven"), "png");

    public static final TestFile<byte[]> FILE_EIGHT = new BinaryFile(
            TEST_PROPERTIES.getProperty("absolutePath.fileEight"), "mp4");


    private static abstract class AbstractTestFile<C> extends AbstractTestFileSystemElement implements TestFile<C> {

        private final String extension;

        protected AbstractTestFile(String absolutePath, String extension) {
            super(absolutePath);

            this.extension = extension;
        }

        @Override
        public String getExtension() {
            return extension;
        }

        @Override
        public InputStream getInputStream() {
            throw new UnsupportedOperationException("A test file does not have an input stream.");
        }
    }

    private static class TextFile extends AbstractTestFile<String> {

        private final String content;

        private TextFile(String absolutePath, String extension) {
            super(absolutePath, extension);

            this.content = readFileToString(absolutePath);
        }

        @Override
        public String getContent() {
            return content;
        }
    }

    private static class BinaryFile extends AbstractTestFile<byte[]> {

        private final byte[] content;

        private BinaryFile(String absolutePath, String extension) {
            super(absolutePath, extension);

            this.content = readFileToByteArray(absolutePath);
        }

        @Override
        public byte[] getContent() {
            return content;
        }
    }
}
