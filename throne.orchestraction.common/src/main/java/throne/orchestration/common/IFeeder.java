package throne.orchestration.common;

import throne.orchestration.common.exception.FeederException;

public interface IFeeder extends IConfigurable {

    public void open() throws FeederException;

    public void close() throws FeederException;

    public void feed(String topic, IData data) throws FeederException;

    public FeederType getType();
}
