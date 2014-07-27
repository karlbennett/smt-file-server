package shiver.me.timbers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import static shiver.me.timbers.Utils.getFile;
import static shiver.me.timbers.Utils.getLastModified;
import static shiver.me.timbers.Utils.openPropertiesFile;

public class Constants {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final Properties TEST_PROPERTIES = openPropertiesFile("test.properties");

    public static final File CURRENT_DIRECTORY_FILE = getFile("");
    public static final String CURRENT_DIRECTORY_ABSOLUTE_PATH = CURRENT_DIRECTORY_FILE.getPath();
    public static final String CURRENT_DIRECTORY_NAME = CURRENT_DIRECTORY_FILE.getName();
    public static final Date CURRENT_DIRECTORY_MODIFICATION_DATE = getLastModified(CURRENT_DIRECTORY_ABSOLUTE_PATH);

    public static final String TEST_DIRECTORY_ABSOLUTE_PATH = TEST_PROPERTIES.getProperty("testDir");
    public static final String TEST_DIRECTORY_PATH =
            TEST_DIRECTORY_ABSOLUTE_PATH.replace(CURRENT_DIRECTORY_ABSOLUTE_PATH, "");
}
