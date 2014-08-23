package shiver.me.timbers.file.io;

import org.springframework.http.MediaType;

public interface TestFile<C> extends TestFileSystemElement, File {

    public MediaType getMediaType();

    public C getContent();
}
