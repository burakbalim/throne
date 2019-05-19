package throne.orchestration.common.exception;

public class FeederException extends OrchestractionException {

    public FeederException(String message) {
        super(message);
    }


    public FeederException(String message, Exception innerException) {
        super(message, innerException);
    }

    public FeederException(String message, Exception e, String name) {
        super(message, e);
    }
}
