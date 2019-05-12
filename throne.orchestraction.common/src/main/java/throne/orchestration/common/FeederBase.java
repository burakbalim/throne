package throne.orchestration.common;

public abstract class FeederBase implements IFeeder {

    protected abstract void produce(IData data);
    private boolean isOpen;

    protected void onOpen() {
        isOpen = true;
    }

    protected void onClose() {
        isOpen = false;
    }

    public void feed(IData data) {
        if (isOpen) {
            produce(data);
        }
    }

}