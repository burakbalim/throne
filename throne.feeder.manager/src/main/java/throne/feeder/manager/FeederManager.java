package throne.feeder.manager;

import throne.orchestration.common.IFeeder;
import throne.orchestration.common.IData;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.manager.OrchestrationManager;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FeederManager implements OrchestrationManager {

    private Logger logger = Logger.getLogger(FeederManager.class.getName());

    private List<IFeeder> feederList;
    private ThreadPoolExecutor executor;
    private boolean isStopSignal;

    @Override
    public void start() {
        isStopSignal = true;
        executor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        for (IFeeder item : feederList) {
            try {
                item.open();
            } catch (FeederException e) {
                logger.log(Level.WARNING, "Feeder initialization exception." + e);
            }
        }
    }

    @Override
    public void stop() throws FeederException {
        isStopSignal = false;
        executor.shutdown();

        for (IFeeder item : feederList) {
            item.close();
        }
    }

    public void setFeederList(List<IFeeder> feederList) {
        this.feederList = feederList;
    }

    public List<IFeeder> getFeederList() {
        return feederList;
    }

    public void feed(IData iData) {
        if (isStopSignal) {
            for (IFeeder feeder : feederList) {
                onFeed(iData, feeder);
            }
        }
    }

    private void onFeed(IData iData, IFeeder feeder) {
        executor.submit(() -> {
            try {
                feeder.feed(iData);
            } catch (Exception e) {
                logger.log(Level.WARNING,"Occurred Exception while feeding. Exception: " +  e.getMessage());
            }
        });
    }
}
