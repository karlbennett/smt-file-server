package shiver.me.timbers.file.io;

import java.util.List;

import static java.util.Collections.emptyList;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.TEST_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.TEST_PROPERTIES;

public class DirectoryConstants {

    private static final List<FileSystemElement> EMPTY_DIRECTORIES = emptyList();
    private static final List<File> EMPTY_FILES = emptyList();

    public static final TestDirectory CURRENT_DIRECTORY = new ConstantTestDirectory(
            CURRENT_DIRECTORY_ABSOLUTE_PATH, EMPTY_DIRECTORIES, EMPTY_FILES);

    public static final TestDirectory TEST_DIRECTORY = new ConstantTestDirectory(
            TEST_DIRECTORY_ABSOLUTE_PATH, EMPTY_DIRECTORIES, EMPTY_FILES);

    public static final TestDirectory DIRECTORY_ONE = new ConstantTestDirectory(
            TEST_PROPERTIES.getProperty("absolutePath.dirone"), EMPTY_DIRECTORIES, EMPTY_FILES);

    public static final TestDirectory DIRECTORY_TWO = new ConstantTestDirectory(
            TEST_PROPERTIES.getProperty("absolutePath.dirtwo"), EMPTY_DIRECTORIES, EMPTY_FILES);

    public static final TestDirectory DIRECTORY_THREE = new ConstantTestDirectory(
            TEST_PROPERTIES.getProperty("absolutePath.dirthree"), EMPTY_DIRECTORIES, EMPTY_FILES);

    public static final TestDirectory DIRECTORY_FOUR = new ConstantTestDirectory(
            TEST_PROPERTIES.getProperty("absolutePath.dirfour"), EMPTY_DIRECTORIES, EMPTY_FILES);

    public static final TestDirectory ORG_DIRECTORY = new ConstantTestDirectory(
            TEST_PROPERTIES.getProperty("absolutePath.org"), EMPTY_DIRECTORIES, EMPTY_FILES);

    public static final TestDirectory SHIVER_DIRECTORY = new ConstantTestDirectory(
            TEST_PROPERTIES.getProperty("absolutePath.shiver"), EMPTY_DIRECTORIES, EMPTY_FILES);

    private static class ConstantTestDirectory extends AbstractTestFileSystemElement implements TestDirectory {

        private final List<FileSystemElement> directories;
        private final List<File> files;

        public ConstantTestDirectory(String absolutePath, List<FileSystemElement> directories, List<File> files) {
            super(absolutePath);

            this.directories = directories;
            this.files = files;
        }

        @Override
        public List<FileSystemElement> getDirectories() {
            return directories;
        }

        @Override
        public List<File> getFiles() {
            return files;
        }
    }
}
