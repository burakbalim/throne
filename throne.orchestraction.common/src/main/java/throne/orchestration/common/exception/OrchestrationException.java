package throne.orchestration.common.exception;

public class OrchestrationException extends Exception {

    private final Throwable innerException;
    private final String message;

    public OrchestrationException(final Throwable innerException, final String message) {
        super(message);
        this.innerException = innerException;
        this.message = message;
    }

    public OrchestrationException(final String message, final Throwable innerException) {
        super(message);
        this.innerException = innerException;
        this.message = message;
    }

    @Override
    public String getMessage() {

        if (innerException != null) {
            return "Message: " + message + " Inner Exception " + innerException.getMessage();
        }

        return message;
    }
}
