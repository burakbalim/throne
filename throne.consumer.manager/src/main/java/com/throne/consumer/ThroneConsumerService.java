package com.throne.consumer;

import throne.orchestration.common.IConsumer;
import throne.orchestration.common.IPlugin;
import throne.orchestration.common.exception.OrchestractionException;
import throne.orchestration.common.util.OrchestractionUtil;

import java.util.ArrayList;
import java.util.List;

public class ThroneConsumerService {
    private ConsumerManager consumerManager;
    private static ThroneConsumerService instance = null;
    private static final Object object = new Object();

    private ThroneConsumerService() {
    }

    public static ThroneConsumerService getInstance() {
        synchronized (object) {
            if (instance == null) {
                instance = new ThroneConsumerService();
            }
        }
        return instance;
    }

    public void init(String configurationPath) throws OrchestractionException {
        consumerManager = new ConsumerManager();
        String mainJson = OrchestractionUtil.readFile(configurationPath);
        ConductorCfg conductorCfg = OrchestractionUtil.readJson(mainJson, ConductorCfg.class);
        List<IConsumer> consumerList = populateConsumerByConfig(conductorCfg.getConsumerCfgList());
        List<IPlugin> pluginList = populatePluginByConfig(conductorCfg.getPluginList());
        consumerManager.init(consumerList, pluginList);
    }

    public void start() {
        consumerManager.start();
    }

    public void stop() {
        consumerManager.stop();
    }

    private List<IPlugin> populatePluginByConfig(List<IPluginCfg> pluginCfgList) throws OrchestractionException {
        List<IPlugin> pluginList = new ArrayList<>();
        for (IPluginCfg pluginCfg : pluginCfgList) {
            IPlugin iPlugin = null;

            if ("ConsolePlugin".equals(pluginCfg.getType())) {
                pluginList.add(OrchestractionUtil.newInstanceOfClass("com.throne.consumer.ConsoleDumpPlugin"));
            }
        }

        return pluginList;
    }

    private List<IConsumer> populateConsumerByConfig(List<ConsumerCfg> consumerCfgList) throws OrchestractionException {
        List<IConsumer> consumerList = new ArrayList<>();
        for (ConsumerCfg consumer : consumerCfgList) {
            IConsumer iConsumer = null;

            if ("KafkaConsumer".equals(consumer.getType())) {
                iConsumer = OrchestractionUtil.newInstanceOfClass("throne.consumer.kafka.KafkaConsumer");
                iConsumer.configure(consumer.getPath());
            }

            consumerList.add(iConsumer);
        }

        return consumerList;
    }

}
