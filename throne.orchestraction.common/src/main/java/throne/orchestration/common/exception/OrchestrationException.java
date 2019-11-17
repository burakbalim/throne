package throne.orchestration.common.exception;

public class OrchestrationException extends Exception {

    private Throwable innerException = null;
    private final String message;

    public OrchestrationException(final String message, final Throwable innerException) {
        super(message);
        this.innerException = innerException;
        this.message = message;
    }

    public OrchestrationException(final String message) {
        super(message);
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
