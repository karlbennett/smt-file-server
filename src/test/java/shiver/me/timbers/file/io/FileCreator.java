package shiver.me.timbers.file.io;

public interface FileCreator extends FileSystemElementCreator {

    public File create(String path);
}
