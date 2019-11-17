package throne.orchestration.common;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FeederBase implements IFeeder {

    private Logger logger = Logger.getLogger(FeederBase.class.getName());

    protected abstract void produce(String topic, IData data);
    private boolean isOpen;

    protected void onOpen() {
        isOpen = true;
    }

    protected void onClose() {
        isOpen = false;
    }

    public void feed(String topic, IData data) {
        if (isOpen) {
            produce(topic, data);
        }
        else {
            logger.log(Level.INFO, "This Feeder not opened. Feeder name: " + name());
        }
    }
}