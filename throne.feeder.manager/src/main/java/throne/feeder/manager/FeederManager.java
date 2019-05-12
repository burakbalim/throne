package throne.feeder.manager;

import throne.orchestration.common.IFeeder;
import throne.orchestration.common.IData;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.manager.OrchestractionManager;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class FeederManager implements OrchestractionManager {

    private List<IFeeder> feederList;
    private ThreadPoolExecutor threadPoolExecutor;
    private boolean isStopSignal;

    @Override
    public void start() {
        isStopSignal = true;
        threadPoolExecutor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

        for (IFeeder item : feederList) {
            try {
                item.open();
            } catch (FeederException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() throws FeederException {
        isStopSignal = false;
        threadPoolExecutor.shutdown();

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
                threadPoolExecutor.submit(() -> {
                    try {
                        feeder.feed(iData);
                    } catch (Exception e) {
                        //TODO log
                        System.out.println(e.getMessage());
                    }
                });
            }
        }
    }
}
