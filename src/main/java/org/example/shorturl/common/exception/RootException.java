package org.example.shorturl.common.exception;

public class RootException extends RuntimeException{

    public RootException() {
        super();
    }

    public RootException(String message) {
        super(message);
    }

    public RootException(String message, Throwable cause) {
        super(message, cause);
    }

    public RootException(Throwable cause) {
        super(cause);
    }

    protected RootException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
