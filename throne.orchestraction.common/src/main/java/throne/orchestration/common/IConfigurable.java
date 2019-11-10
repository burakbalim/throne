package throne.orchestration.common;


import throne.orchestration.common.exception.ConsumerException;
import throne.orchestration.common.exception.FeederException;

public interface IConfigurable {

    public void configure(String path) throws ConsumerException;
}
