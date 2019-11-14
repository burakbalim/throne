package throne.orchestration.common;


import throne.orchestration.common.exception.ConsumerException;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.exception.OrchestrationException;

public interface IConfigurable {

    public void configure(String path) throws OrchestrationException;
}
