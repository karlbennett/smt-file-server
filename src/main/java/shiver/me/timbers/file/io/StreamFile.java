package shiver.me.timbers.file.io;

import java.io.InputStream;

/**
 * A {@link File} that provides access to it's {@link InputStream}.
 *
 * @author Karl Bennett
 */
public interface StreamFile extends File {

    InputStream getInputStream();
}
