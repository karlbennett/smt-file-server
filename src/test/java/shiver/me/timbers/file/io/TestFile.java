package shiver.me.timbers.file.io;

import org.springframework.http.MediaType;

public interface TestFile<C> extends TestFileSystemElement, StreamFile {

    public MediaType getMediaType();

    public C getContent();
}
