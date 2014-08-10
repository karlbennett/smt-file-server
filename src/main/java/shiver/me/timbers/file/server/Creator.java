package shiver.me.timbers.file.server;

/**
 * Simple interface used to defer the creation of classes.
 *
 * @author Karl Bennett
 */
public interface Creator<T> {

    public T create();
}
