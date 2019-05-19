package throne.orchestration.common;

import throne.orchestration.common.exception.ConsumerException;

import java.util.List;

public abstract class ConsumerBase implements IConsumer {

    private boolean isOpen;
    private ILogger ILogger;
    protected abstract List<IData> onConsume() throws ConsumerException;

    protected void onOpen() {
        isOpen = true;
    }

    protected void onStop() {
        isOpen = false;
    }

    public List<IData> consume() throws ConsumerException {
        if (isOpen) {
            return this.onConsume();
        } else {
            throw new ConsumerException("Consumer is not running");
        }
    }

    @Override
    public void setILogger(ILogger ILogger) {
        this.ILogger = ILogger;
    }

    @Override
    public boolean state() {
        return isOpen;
    }

    protected ILogger getILogger() {
        return this.ILogger;
    }

}
