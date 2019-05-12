package throne.orchestration.common;

import throne.orchestration.common.exception.ConsumerException;

public interface IConsumer extends IConfigurable {

    public void start() throws ConsumerException;

    public void stop() throws ConsumerException;

    public void consume() throws ConsumerException, ConsumerException;

    public boolean state();

    public void setPluginManager();
}
