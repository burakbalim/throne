package throne.feeder.manager;

import throne.orchestration.common.IFeeder;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.exception.OrchestrationException;
import throne.orchestration.common.util.OrchestrationUtil;

import java.util.ArrayList;
import java.util.List;

public class FeederService {

    private static final Object object = new Object();

    private FeederManager feederManager;
    private static FeederService instance = null;

    private FeederService() {
    }

    public static FeederService getInstance() {
        synchronized (object) {
            if (instance == null) {
                instance = new FeederService();
            }
        }
        return instance;
    }

    public void init(String configurationPath) throws OrchestrationException {
        String mainJson = OrchestrationUtil.readFile(configurationPath);
        ConductorConfig conductorConfig = OrchestrationUtil.readJson(mainJson, ConductorConfig.class);
        List<IFeeder> feeder = getFeeder(conductorConfig.getFeeder());

        feederManager = new FeederManager();
        feederManager.setFeederList(feeder);
    }

    private List<IFeeder> getFeeder(List<FeederConfig> feederList) throws OrchestrationException {
        List<IFeeder> feeders = new ArrayList<>();
        for (FeederConfig feederConfig : feederList) {
            if (feederConfig.getFeederType().equals("KafkaFeeder")) {
                try {
                    IFeeder feeder = (IFeeder) Class.forName("throne.feeder.kafka.KafkaFeeder").newInstance();
                    feeder.configure(feederConfig.getFeederPath());
                    feeders.add(feeder);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException  e) {
                    throw new OrchestrationException("Occurred Exception while setting it up kafka" , e);
                }
            }
        }
        return feeders;
    }

    public void start() {
        feederManager.start();
    }

    public void stop() throws FeederException {
        feederManager.stop();
    }

    public FeederManager getFeederManager() {
        return feederManager;
    }
}
