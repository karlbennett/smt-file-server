package shiver.me.timbers.file.io;

import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

public abstract class AbstractFileCreator<F extends File> implements FileCreator<F> {

    @Override
    public F mock(String name, Date modified) {

        return mock(name, modified, "txt", 100, TEXT_PLAIN_VALUE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public F mock(String name, Date modified, String extension, long size, String mimeType) {

        final File mock = Mockito.mock(type());
        when(mock.getName()).thenReturn(name);
        when(mock.getModified()).thenReturn(modified);
        when(mock.getExtension()).thenReturn(extension);
        when(mock.getSize()).thenReturn(size);

        return (F) mock;
    }

    @SuppressWarnings("unchecked")
    public <R extends File> Class<R> type() {

        return (Class<R>) File.class;
    }
}
