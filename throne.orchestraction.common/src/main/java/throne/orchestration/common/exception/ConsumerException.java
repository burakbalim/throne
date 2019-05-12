package throne.orchestration.common.exception;

public class ConsumerException extends OrchestractionException {

    public ConsumerException(String message, Throwable cause, String sender) {
        super(message, cause, sender);
    }

    public ConsumerException(String message, String sender) {
        super(message, sender);
    }

    public ConsumerException(String message) {
        super(message);
    }
}
