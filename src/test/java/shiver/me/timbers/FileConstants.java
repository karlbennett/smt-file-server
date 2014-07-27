package shiver.me.timbers;

import java.util.Date;

import static shiver.me.timbers.DirectoryConstants.DIRECTORY_FOUR_PATH;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_ONE_PATH;
import static shiver.me.timbers.DirectoryConstants.DIRECTORY_TWO_PATH;
import static shiver.me.timbers.DirectoryConstants.buildAbsolutePath;
import static shiver.me.timbers.DirectoryConstants.buildTestPath;
import static shiver.me.timbers.Utils.buildPath;
import static shiver.me.timbers.Utils.getLastModified;
import static shiver.me.timbers.Utils.readFileToByteArray;
import static shiver.me.timbers.Utils.readFileToString;

public class FileConstants {

    public static final String FILE_EXTENSION = "txt";

    public static final String TEST_PROPERTIES_FILE_NAME = "test.properties";
    public static final String TEST_PROPERTIES_FILE_PATH = buildTestPath(TEST_PROPERTIES_FILE_NAME);
    public static final String TEST_PROPERTIES_FILE_ABSOLUTE_PATH = buildAbsolutePath(TEST_PROPERTIES_FILE_PATH);

    public static final String FILE_ONE_NAME = "FileOne.txt";
    public static final String FILE_ONE_PATH = buildTestPath(FILE_ONE_NAME);
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

    public static final String FILE_FIVE_NAME = "FileFive.json";
    public static final String FILE_FIVE_EXTENSION = "json";
    public static final String FILE_FIVE_PATH = buildTestPath(FILE_FIVE_NAME);
    public static final String FILE_FIVE_ABSOLUTE_PATH = buildAbsolutePath(FILE_FIVE_PATH);
    public static final Date FILE_FIVE_MODIFICATION_DATE = getLastModified(FILE_FIVE_ABSOLUTE_PATH);
    public static final String FILE_FIVE_TEXT = readFileToString(FILE_FIVE_ABSOLUTE_PATH);

    public static final String FILE_SIX_NAME = "FileSix.xml";
    public static final String FILE_SIX_EXTENSION = "xml";
    public static final String FILE_SIX_PATH = buildTestPath(FILE_SIX_NAME);
    public static final String FILE_SIX_ABSOLUTE_PATH = buildAbsolutePath(FILE_SIX_PATH);
    public static final Date FILE_SIX_MODIFICATION_DATE = getLastModified(FILE_SIX_ABSOLUTE_PATH);
    public static final String FILE_SIX_TEXT = readFileToString(FILE_SIX_ABSOLUTE_PATH);

    public static final String FILE_SEVEN_NAME = "FileSeven.png";
    public static final String FILE_SEVEN_EXTENSION = "png";
    public static final String FILE_SEVEN_PATH = buildTestPath(FILE_SEVEN_NAME);
    public static final String FILE_SEVEN_ABSOLUTE_PATH = buildAbsolutePath(FILE_SEVEN_PATH);
    public static final Date FILE_SEVEN_MODIFICATION_DATE = getLastModified(FILE_SEVEN_ABSOLUTE_PATH);
    public static final byte[] FILE_SEVEN_CONTENT = readFileToByteArray(FILE_SEVEN_ABSOLUTE_PATH);

    public static final String FILE_EIGHT_NAME = "FileEight.mp4";
    public static final String FILE_EIGHT_EXTENSION = "mp4";
    public static final String FILE_EIGHT_PATH = buildTestPath(FILE_EIGHT_NAME);
    public static final String FILE_EIGHT_ABSOLUTE_PATH = buildAbsolutePath(FILE_EIGHT_PATH);
    public static final Date FILE_EIGHT_MODIFICATION_DATE = getLastModified(FILE_EIGHT_ABSOLUTE_PATH);
    public static final byte[] FILE_EIGHT_CONTENT = readFileToByteArray(FILE_EIGHT_ABSOLUTE_PATH);
}
