package shiver.me.timbers.file.server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ServerConstants {

    public static DateFormat dateFormat() {

        return new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
    }
}
