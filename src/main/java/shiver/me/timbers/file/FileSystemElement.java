package shiver.me.timbers.file;

import java.util.Date;

/**
 * A simple file system element, it contains the name, extension and creation date of the file.
 *
 * @author Karl Bennett
 */
public interface FileSystemElement {

    public String getName();

    public String getExtension();

    public Date getModified();
}
