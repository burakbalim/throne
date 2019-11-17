package throne.orchestration.common.exception;

public class ConsumerException extends OrchestrationException {

    public ConsumerException(String message) {
        super(message);
    }

    public ConsumerException(String message, Exception innerException) {
        super(message, innerException);
    }

    public ConsumerException(String s, Exception e, String name) {
        super(s, e);
    }

}
