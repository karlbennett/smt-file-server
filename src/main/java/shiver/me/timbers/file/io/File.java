package shiver.me.timbers.file.io;

import java.io.InputStream;

/**
 * A {@link FileSystemElement} that can have it's contents read.
 *
 * @author Karl Bennett
 */
public interface File extends FileSystemElement {

    public String getExtension();

    public long getSize();

    public InputStream getInputStream();
}
