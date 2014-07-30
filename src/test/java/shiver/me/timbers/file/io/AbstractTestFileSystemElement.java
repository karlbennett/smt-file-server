package shiver.me.timbers.file.io;

import java.util.Date;

import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_ABSOLUTE_PATH;
import static shiver.me.timbers.Utils.getFile;

public abstract class AbstractTestFileSystemElement implements TestFileSystemElement {

    private final String path;
    private final String absolutePath;
    private final String name;
    private final Date modified;

    protected AbstractTestFileSystemElement(String absolutePath) {

        final java.io.File file = getFile(absolutePath);

        this.path = file.getPath().replace(CURRENT_DIRECTORY_ABSOLUTE_PATH, "");
        this.absolutePath = file.getPath();
        this.name = file.getName();
        this.modified = new Date(file.lastModified());
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public String getAbsolutePath() {
        return absolutePath;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getModified() {
        return modified;
    }
}
