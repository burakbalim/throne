package throne.feeder.manager;

import throne.orchestration.common.IFeeder;
import throne.orchestration.common.exception.ConsumerException;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.exception.OrchestractionException;
import throne.orchestration.common.util.OrchestractionUtil;

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

    public void init(String configurationPath) throws OrchestractionException {
        feederManager = new FeederManager();
        String mainJson = OrchestractionUtil.readFile(configurationPath);
        ConductorCfg conductorCfg = OrchestractionUtil.readJson(mainJson, ConductorCfg.class);
        List<IFeeder> feeder = getFeeder(conductorCfg.getFeeder());
        feederManager.setFeederList(feeder);
    }

    private List<IFeeder> getFeeder(List<FeederCfg> feederList) throws OrchestractionException {
        List<IFeeder> feeders = new ArrayList<>();
        for (FeederCfg feederCfg : feederList) {
            if (feederCfg.getFeederType().equals("KafkaFeeder")) {
                try {
                    IFeeder feeder = (IFeeder) Class.forName("throne.feeder.kafka.KafkaFeeder").newInstance();
                    feeder.configure(feederCfg.getFeederPath());
                    feeders.add(feeder);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | ConsumerException | FeederException e) {
                    throw new OrchestractionException("Occurred Exception while kafka Feeder" , e);
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
