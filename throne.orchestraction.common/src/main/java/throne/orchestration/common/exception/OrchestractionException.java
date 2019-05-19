package throne.orchestration.common.exception;

public class OrchestractionException extends Exception {

    private Exception innerException;
    private String message;

    public OrchestractionException(String message) {
        super(message);
        this.message = message;
    }

    public OrchestractionException(String message, Exception innerException) {
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
