package com.efindi.batch.commons.function;

public class VoidMethodExecutionException extends RuntimeException {
    public VoidMethodExecutionException() {
        super();
    }

    public VoidMethodExecutionException(String message) {
        super(message);
    }

    public VoidMethodExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public VoidMethodExecutionException(Throwable cause) {
        super(cause);
    }

    protected VoidMethodExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
