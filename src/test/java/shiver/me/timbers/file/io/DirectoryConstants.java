package shiver.me.timbers.file.io;

import java.util.Date;

import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.Constants.TEST_DIRECTORY_PATH;
import static shiver.me.timbers.Utils.buildPath;
import static shiver.me.timbers.Utils.getLastModified;

public class DirectoryConstants {

    public static final String DIRECTORY_ONE_NAME = "dirone";
    public static final String DIRECTORY_ONE_PATH = buildTestPath(DIRECTORY_ONE_NAME);
    public static final String DIRECTORY_ONE_ABSOLUTE_PATH = buildAbsolutePath(DIRECTORY_ONE_PATH);
    public static final Date DIRECTORY_ONE_MODIFICATION_DATE = getLastModified(DIRECTORY_ONE_ABSOLUTE_PATH);

    public static final String DIRECTORY_TWO_NAME = "dirtwo";
    public static final String DIRECTORY_TWO_PATH = buildPath(DIRECTORY_ONE_PATH, DIRECTORY_TWO_NAME);
    public static final String DIRECTORY_TWO_ABSOLUTE_PATH = buildAbsolutePath(DIRECTORY_TWO_PATH);
    public static final Date DIRECTORY_TWO_MODIFICATION_DATE = getLastModified(DIRECTORY_TWO_ABSOLUTE_PATH);

    public static final String DIRECTORY_THREE_NAME = "dirthree";
    public static final String DIRECTORY_THREE_PATH = buildTestPath(DIRECTORY_THREE_NAME);
    public static final String DIRECTORY_THREE_ABSOLUTE_PATH = buildAbsolutePath(DIRECTORY_THREE_PATH);
    public static final Date DIRECTORY_THREE_MODIFICATION_DATE = getLastModified(DIRECTORY_THREE_ABSOLUTE_PATH);

    public static final String DIRECTORY_FOUR_NAME = ".dirfour";
    public static final String DIRECTORY_FOUR_PATH = buildPath(DIRECTORY_THREE_PATH, DIRECTORY_FOUR_NAME);
    public static final String DIRECTORY_FOUR_ABSOLUTE_PATH = buildAbsolutePath(DIRECTORY_FOUR_PATH);
    public static final Date DIRECTORY_FOUR_MODIFICATION_DATE = getLastModified(DIRECTORY_FOUR_ABSOLUTE_PATH);

    public static String buildAbsolutePath(String... parts) {

        return buildPath(CURRENT_DIRECTORY_ABSOLUTE_PATH, buildPath(parts));
    }

    public static String buildTestPath(String path) {

        return buildPath(TEST_DIRECTORY_PATH, buildPath(path));
    }
}
