package throne.orchestration.common.exception;

public class FeederException extends OrchestractionException {

    public FeederException(String message, Throwable cause, String sender) {
        super(message, cause, sender);
    }

    public FeederException(String message, String sender) {
        super(message, sender);
    }

}
