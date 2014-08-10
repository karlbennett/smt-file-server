package shiver.me.timbers.file.io;

import java.util.Date;

public interface FileSystemElementCreator {

    public FileSystemElement create(String path);

    public FileSystemElement mock(String name, Date modified);
}
