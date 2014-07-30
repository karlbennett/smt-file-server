package shiver.me.timbers;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static java.lang.String.format;
import static shiver.me.timbers.Utils.getFile;

public class Constants {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final Properties TEST_PROPERTIES = openPropertiesFile("test.properties");

    public static final File CURRENT_DIRECTORY_FILE = getFile("");
    public static final String CURRENT_DIRECTORY_ABSOLUTE_PATH = CURRENT_DIRECTORY_FILE.getPath();

    public static final String TEST_DIRECTORY_ABSOLUTE_PATH = TEST_PROPERTIES.getProperty("testDir");

    private static Properties openPropertiesFile(String name) {

        final URL url = Thread.currentThread().getContextClassLoader().getResource(name);

        if (null == url) {

            throw new AssertionError(format("Could not open properties file: %s", name));
        }

        try {

            final Properties properties = new Properties();
            properties.load(url.openStream());

            return properties;

        } catch (IOException e) {

            throw new AssertionError(e);
        }
    }
}
