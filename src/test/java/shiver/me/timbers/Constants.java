package shiver.me.timbers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import static java.io.File.separator;

public class Constants {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final File CURRENT_DIRECTORY_FILE = getCurrentDirectory();
    public static final String CURRENT_DIRECTORY_ABSOLUTE_PATH = CURRENT_DIRECTORY_FILE.getPath();
    public static final String CURRENT_DIRECTORY_NAME = CURRENT_DIRECTORY_FILE.getName();
    public static final Date CURRENT_DIRECTORY_MODIFICATION_DATE = getLastModified(CURRENT_DIRECTORY_ABSOLUTE_PATH);

    public static final String DIRECTORY_ONE_NAME = "dirone";
    public static final String DIRECTORY_ONE_PATH = DIRECTORY_ONE_NAME;
    public static final String DIRECTORY_ONE_ABSOLUTE_PATH = buildAbsolutePath(DIRECTORY_ONE_PATH);
    public static final Date DIRECTORY_ONE_MODIFICATION_DATE = getLastModified(DIRECTORY_ONE_ABSOLUTE_PATH);

    public static final String DIRECTORY_TWO_NAME = "dirtwo";
    public static final String DIRECTORY_TWO_PATH = buildPath(DIRECTORY_ONE_PATH, DIRECTORY_TWO_NAME);
    public static final String DIRECTORY_TWO_ABSOLUTE_PATH = buildAbsolutePath(DIRECTORY_TWO_PATH);
    public static final Date DIRECTORY_TWO_MODIFICATION_DATE = getLastModified(DIRECTORY_TWO_ABSOLUTE_PATH);

    public static final String DIRECTORY_THREE_NAME = "dirthree";
    public static final String DIRECTORY_THREE_PATH = DIRECTORY_THREE_NAME;
    public static final String DIRECTORY_THREE_ABSOLUTE_PATH = buildAbsolutePath(DIRECTORY_THREE_PATH);
    public static final Date DIRECTORY_THREE_MODIFICATION_DATE = getLastModified(DIRECTORY_THREE_ABSOLUTE_PATH);

    public static final String DIRECTORY_FOUR_NAME = ".dirfour";
    public static final String DIRECTORY_FOUR_PATH = buildPath(DIRECTORY_THREE_PATH, DIRECTORY_FOUR_NAME);
    public static final String DIRECTORY_FOUR_ABSOLUTE_PATH = buildAbsolutePath(DIRECTORY_FOUR_PATH);
    public static final Date DIRECTORY_FOUR_MODIFICATION_DATE = getLastModified(DIRECTORY_FOUR_ABSOLUTE_PATH);

    public static final String FILE_EXTENSION = "txt";

    public static final String FILE_ONE_NAME = "FileOne.txt";
    public static final String FILE_ONE_PATH = FILE_ONE_NAME;
    public static final String FILE_ONE_ABSOLUTE_PATH = buildAbsolutePath(FILE_ONE_PATH);
    public static final Date FILE_ONE_MODIFICATION_DATE = getLastModified(FILE_ONE_ABSOLUTE_PATH);
    public static final String FILE_ONE_TEXT = readFileToString(FILE_ONE_ABSOLUTE_PATH);

    public static final String FILE_TWO_NAME = ".FileTwo";
    public static final String FILE_TWO_PATH = buildPath(DIRECTORY_ONE_PATH, FILE_TWO_NAME);
    public static final String FILE_TWO_ABSOLUTE_PATH = buildAbsolutePath(FILE_TWO_PATH);
    public static final Date FILE_TWO_MODIFICATION_DATE = getLastModified(FILE_TWO_ABSOLUTE_PATH);
    public static final String FILE_TWO_TEXT = readFileToString(FILE_TWO_ABSOLUTE_PATH);

    public static final String FILE_THREE_NAME = ".FileThree.txt";
    public static final String FILE_THREE_PATH = buildPath(DIRECTORY_TWO_PATH, FILE_THREE_NAME);
    public static final String FILE_THREE_ABSOLUTE_PATH = buildAbsolutePath(FILE_THREE_PATH);
    public static final Date FILE_THREE_MODIFICATION_DATE = getLastModified(FILE_THREE_ABSOLUTE_PATH);
    public static final String FILE_THREE_TEXT = readFileToString(FILE_THREE_ABSOLUTE_PATH);

    public static final String FILE_FOUR_NAME = "FileFour.txt";
    public static final String FILE_FOUR_PATH = buildPath(DIRECTORY_FOUR_PATH, FILE_FOUR_NAME);
    public static final String FILE_FOUR_ABSOLUTE_PATH = buildAbsolutePath(FILE_FOUR_PATH);
    public static final Date FILE_FOUR_MODIFICATION_DATE = getLastModified(FILE_FOUR_ABSOLUTE_PATH);
    public static final String FILE_FOUR_TEXT = readFileToString(FILE_FOUR_ABSOLUTE_PATH);

    public static String buildAbsolutePath(String path) {

        return buildPath(CURRENT_DIRECTORY_ABSOLUTE_PATH, path);
    }

    public static String buildPath(String... parts) {

        final StringBuilder path = new StringBuilder();

        for (String part : parts) {

            path.append(separator).append(part);
        }

        path.replace(0, 1, "");

        return path.toString();
    }

    @SuppressWarnings("ConstantConditions")
    private static File getCurrentDirectory() {

        final URL url = Thread.currentThread().getContextClassLoader().getResource("");

        return getFile(url.getPath());
    }

    private static Date getLastModified(String path) {

        final long lastModified = getFile(path).lastModified();

        return new Date(lastModified);
    }

    private static File getFile(String path) {

        try {

            return new File(path).getCanonicalFile();

        } catch (IOException e) {

            throw new RuntimeException("Could not get the current directory in: " + Constants.class.getName(), e);
        }
    }

    private static String readFileToString(String path) {

        try {

            return FileUtils.readFileToString(new File(path));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
