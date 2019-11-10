package com.throne.consumer;

import com.throne.plugin.PluginConfig;
import throne.orchestration.common.IConsumer;
import throne.orchestration.common.IPlugin;
import throne.orchestration.common.exception.OrchestrationException;
import throne.orchestration.common.util.OrchestrationUtil;

import java.util.ArrayList;
import java.util.List;

public class ConsumerService {

    private static final Object object = new Object();

    private static ConsumerService instance;
    private ConsumerManager consumerManager;

    private ConsumerService() {
    }

    public static ConsumerService getInstance() {
        synchronized (object) {
            if (instance == null) {
                instance = new ConsumerService();
            }
        }
        return instance;
    }

    public void init(String configurationPath) throws OrchestrationException {
        String mainJson = OrchestrationUtil.readFile(configurationPath);
        ConductorConfig conductorConfig = OrchestrationUtil.readJson(mainJson, ConductorConfig.class);
        List<IConsumer> consumerList = populateConsumerByConfig(conductorConfig.getConsumers());
        List<IPlugin> pluginList = populatePluginByConfig(conductorConfig.getPlugins());

        consumerManager = new ConsumerManager();
        consumerManager.init(consumerList, pluginList);
    }

    public void start() {
        consumerManager.start();
    }

    public void stop() {
        consumerManager.stop();
    }

    private List<IPlugin> populatePluginByConfig(List<PluginConfig> pluginCfgList) throws OrchestrationException {
        List<IPlugin> pluginList = new ArrayList<>();
        for (PluginConfig pluginCfg : pluginCfgList) {
            if ("ConsolePlugin".equals(pluginCfg.getType())) {
                pluginList.add(OrchestrationUtil.newInstanceOfClass("com.throne.plugin.ConsoleDumpPlugin"));
            }
        }

        return pluginList;
    }

    private List<IConsumer> populateConsumerByConfig(List<ConsumerConfig> cfgs) throws OrchestrationException {
        List<IConsumer> consumerList = new ArrayList<>();
        for (ConsumerConfig consumer : cfgs) {
            IConsumer iConsumer = null;

            if (ConsumerType.KafkaConsumer.getName().equals(consumer.getType())) {
                iConsumer = OrchestrationUtil.newInstanceOfClass("throne.consumer.kafka.KafkaConsumer");
                iConsumer.configure(consumer.getPath());
            }

            consumerList.add(iConsumer);
        }

        return consumerList;
    }

}
