package shiver.me.timbers.file.io;

import java.util.Date;

public interface FileSystemElementCreator<F extends FileSystemElement> {

    public F create(String path);

    public F mock(String name, Date modified);
}
