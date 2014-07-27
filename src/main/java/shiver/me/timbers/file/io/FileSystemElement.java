package shiver.me.timbers.file.io;

import java.util.Date;

/**
 * A simple file system element, it contains the name and creation date of the file.
 *
 * @author Karl Bennett
 */
public interface FileSystemElement {

    public String getName();

    public Date getModified();
}
