package throne.orchestration.common.exception;

public class OrchestractionException extends Exception {

    private Exception innerException;
    private String message;

    public OrchestractionException(String message, Exception innerException) {
        super(message);
        this.innerException = innerException;
    }

    public String getMessage() {
        return "Message: " + super.getMessage() + " Inner Exception " + innerException.getMessage();
    }
}
