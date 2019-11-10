package throne.orchestration.common.exception;

public class OrchestrationException extends Exception {

    private Exception innerException;
    private String message;

    public OrchestrationException(String message) {
        super(message);
        this.message = message;
    }

    public OrchestrationException(String message, Exception innerException) {
        super(message);
        this.innerException = innerException;
    }

    public String getMessage() {

        if (innerException != null) {
            return "Message: " + message + " Inner Exception " + innerException.getMessage();
        }

        return message;
    }
}
