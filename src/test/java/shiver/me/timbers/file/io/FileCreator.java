package shiver.me.timbers.file.io;

import java.util.Date;

public interface FileCreator extends FileSystemElementCreator {

    public File create(String path);

    public File mock(String name, Date modified, String extension, long size);
}
