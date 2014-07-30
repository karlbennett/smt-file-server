package shiver.me.timbers;

import java.io.File;
import java.io.IOException;

public class Utils {

    public static File getFile(String path) {

        try {

            return new File(path).getCanonicalFile();

        } catch (IOException e) {

            throw new RuntimeException("Could not get the current directory in: " + Utils.class.getName(), e);
        }
    }
}
