package shiver.me.timbers.file.io;

import java.util.Date;

public interface FileCreator<F extends File> extends FileSystemElementCreator<F> {

    public F create(String path);

    public F mock(String name, Date modified, String extension, long size, String mimeType);
}
