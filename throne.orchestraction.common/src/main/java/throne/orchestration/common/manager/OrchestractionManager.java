package throne.orchestration.common.manager;

import throne.orchestration.common.exception.FeederException;

public interface OrchestractionManager {


    public void start() throws FeederException;

    public void stop() throws FeederException, FeederException;
}
