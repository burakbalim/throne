package throne.orchestration.common;

import throne.orchestration.common.exception.ConsumerException;

import java.util.List;

public abstract class ConsumerBase implements IConsumer {

    private boolean isOpen;
    private boolean isActive;
    private ILogger ILogger;
    protected abstract List<IData> onConsume() throws ConsumerException;
    private IPluginManager iPluginManager;

    protected void onOpen() {
        isOpen = true;
    }

    protected void onStop() {
        isOpen = false;
    }

    public void consume() throws ConsumerException {
        if (isOpen) {
            isActive = false;
            List<IData> iData = this.onConsume();
            send(iData);
            isActive = true;
        } else {
            throw new ConsumerException("Consumer is not running");
        }
    }

    private void send(List<IData> iData) {
        if (iPluginManager != null) {
             iPluginManager.submit(iData);
        }
        else {
            //TODO warning log
        }
    }

    @Override
    public void setILogger(ILogger ILogger) {
        this.ILogger = ILogger;
    }

    @Override
    public boolean state() {
        return isActive;
    }

    protected ILogger getILogger() {
        return this.ILogger;
    }

}
