package shiver.me.timbers.file.server.spring.exception;

/**
 * @author Karl Bennett
 */
public class Error {

    private final String error;

    public Error(Throwable throwable) {
        this(throwable.getMessage());
    }

    public Error(String error) {

        if (null == error) {
            throw new NullPointerException("An error must not have a null error.");
        }

        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Error)) {
            return false;
        }

        final Error that = (Error) o;

        if (!error.equals(that.error)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {

        return error.hashCode();
    }
}
