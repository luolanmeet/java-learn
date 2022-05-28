package io.seata.common.exception;

/**
 * The type Should never happen exception.
 */
public class ShouldNeverHappenException extends RuntimeException {

    /**
     * Instantiates a new Should never happen exception.
     */
    public ShouldNeverHappenException() {
        super();
    }

    /**
     * Instantiates a new Should never happen exception.
     *
     * @param message the message
     */
    public ShouldNeverHappenException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Should never happen exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ShouldNeverHappenException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Should never happen exception.
     *
     * @param cause the cause
     */
    public ShouldNeverHappenException(Throwable cause) {
        super(cause);
    }
}
