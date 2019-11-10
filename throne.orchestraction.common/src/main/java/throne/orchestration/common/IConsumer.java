package throne.orchestration.common;

import throne.orchestration.common.exception.ConsumerException;

import java.util.List;

public interface IConsumer extends IConfigurable {

    public void open() throws ConsumerException;

    public void close() throws ConsumerException;

    public List<IData> consume() throws ConsumerException;

    public boolean state();

}
