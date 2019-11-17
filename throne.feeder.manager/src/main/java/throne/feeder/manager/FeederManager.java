package throne.feeder.manager;

import throne.orchestration.common.FeederType;
import throne.orchestration.common.IFeeder;
import throne.orchestration.common.IData;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.manager.OrchestrationManager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FeederManager implements OrchestrationManager {

    private Logger logger = Logger.getLogger(FeederManager.class.getName());

    private List<IFeeder> feederList;
    private ThreadPoolExecutor executor;
    private boolean isStopSignal;
    private Map<FeederType, List<IFeeder>> mapToType;

    @Override
    public void start() {
        isStopSignal = true;
        mapToType = feederList.stream().collect(Collectors.groupingBy(IFeeder::getType));
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

    public void feed(String topic, IData iData) {
        if (isStopSignal) {
            for (IFeeder feeder : feederList) {
                onFeed(iData, feeder, topic);
            }
        }
    }

    public void feed(String topic, IData iData, FeederType feederType) {
        if (isStopSignal) {
            List<IFeeder> iFeeders = mapToType.get(feederType);
            for (IFeeder feeder : iFeeders) {
                onFeed(iData, feeder, topic);
            }
        } else {
            logger.log(Level.INFO, "Feeder Manager is stopped");
        }
    }

    private void onFeed(IData iData, IFeeder feeder, String topic) {
        try {
            feeder.feed(topic, iData);
        } catch (FeederException e) {
            e.printStackTrace();
        }
    }
}
