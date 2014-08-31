package shiver.me.timbers.file.io;

import java.util.Date;

public interface StreamFileCreator<S extends StreamFile> extends FileSystemElementCreator<S> {

    public S create(String path);

    public S mock(String name, Date modified, String extension, long size, String mimeType);
}
