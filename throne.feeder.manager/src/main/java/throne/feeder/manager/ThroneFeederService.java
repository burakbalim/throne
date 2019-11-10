package throne.feeder.manager;

import throne.orchestration.common.IFeeder;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.exception.OrchestrationException;
import throne.orchestration.common.util.OrchestrationUtil;

import java.util.ArrayList;
import java.util.List;

public class ThroneFeederService {

    private FeederManager feederManager;
    private static ThroneFeederService instance = null;
    private static final Object object = new Object();

    private ThroneFeederService() {
    }

    public static ThroneFeederService getInstance() {
        synchronized (object) {
            if (instance == null) {
                instance = new ThroneFeederService();
            }
        }
        return instance;
    }

    public void init(String configurationPath) throws OrchestrationException {
        String mainJson = OrchestrationUtil.readFile(configurationPath);
        ConductorCfg conductorCfg = OrchestrationUtil.readJson(mainJson, ConductorCfg.class);
        List<IFeeder> feeder = getFeeder(conductorCfg.getFeeder());

        feederManager = new FeederManager();
        feederManager.setFeederList(feeder);
    }

    private List<IFeeder> getFeeder(List<FeederCfg> feederList) throws OrchestrationException {
        List<IFeeder> feeders = new ArrayList<>();
        for (FeederCfg feederCfg : feederList) {
            if (feederCfg.getFeederType().equals("KafkaFeeder")) {
                try {
                    IFeeder feeder = (IFeeder) Class.forName("throne.feeder.kafka.KafkaFeeder").newInstance();
                    feeder.configure(feederCfg.getFeederPath());
                    feeders.add(feeder);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException  e) {
                    throw new OrchestrationException("Occurred Exception while kafka Feeder" , e);
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
