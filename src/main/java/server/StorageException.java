package server;
public class StorageException extends Exception {

    private static final long serialVersionUID = 2673470763439223267L;

    public StorageException() {
    }

    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
