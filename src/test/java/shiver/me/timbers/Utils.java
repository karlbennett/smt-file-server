package shiver.me.timbers;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

import static java.io.File.separator;
import static java.lang.String.format;

public class Utils {

    public static String buildPath(String... parts) {

        final StringBuilder path = new StringBuilder();

        for (String part : parts) {

            path.append(separator).append(part);
        }

        return path.toString().replaceAll("//", "/");
    }

    public static Properties openPropertiesFile(String name) {

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

    public static Date getLastModified(String path) {

        final long lastModified = getFile(path).lastModified();

        return new Date(lastModified);
    }

    public static File getFile(String path) {

        try {

            return new File(path).getCanonicalFile();

        } catch (IOException e) {

            throw new RuntimeException("Could not get the current directory in: " + Utils.class.getName(), e);
        }
    }

    public static String readFileToString(String path) {

        try {

            return FileUtils.readFileToString(new File(path));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    public static byte[] readFileToByteArray(String path) {

        try {

            return FileUtils.readFileToByteArray(new File(path));

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }
}
