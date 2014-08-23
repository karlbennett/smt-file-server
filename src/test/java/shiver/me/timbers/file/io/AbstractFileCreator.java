package shiver.me.timbers.file.io;

import org.mockito.Mockito;

import java.util.Date;

import static org.mockito.Mockito.when;

public abstract class AbstractFileCreator implements FileCreator {

    @Override
    public FileSystemElement mock(String name, Date modified) {

        return mock(name, modified, "txt", 100);
    }

    @Override
    public File mock(String name, Date modified, String extension, long size) {

        final File mock = Mockito.mock(type());
        when(mock.getName()).thenReturn(name);
        when(mock.getModified()).thenReturn(modified);
        when(mock.getExtension()).thenReturn(extension);
        when(mock.getSize()).thenReturn(size);

        return mock;
    }

    @SuppressWarnings("unchecked")
    public <R extends File> Class<R> type() {

        return (Class<R>) File.class;
    }
}
