package com.throne.consumer;

import throne.orchestration.common.IConsumer;
import throne.orchestration.common.IData;
import throne.orchestration.common.IPlugin;
import throne.orchestration.common.exception.ConsumerException;
import throne.orchestration.common.manager.OrchestrationManager;

import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

class ConsumerManager implements OrchestrationManager {

    private Logger logger = Logger.getLogger(ConsumerManager.class.getName());

    private ExecutorService executor = new ThreadPoolExecutor(10, 50, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private PluginManager pluginManager;

    private List<IConsumer> consumerList;

    private boolean isStopSignal;

    public ConsumerManager() {
        pluginManager = PluginManager.getInstance();
    }

    public void init(List<IConsumer> consumerList, List<IPlugin> plugins) {
        this.consumerList = consumerList;
        this.pluginManager.add(plugins);
    }

    @Override
    public void start() {
        this.consumerList.forEach(consumer -> {
            try {
                consumer.open();
            } catch (ConsumerException e) {
                logger.log(Level.WARNING, "Occurred Exception while consumer opening." + e);
            }
        });

        isStopSignal = false;
        Thread consumerManagerThread = new Thread(this::process, "Consumer Manager Main-Thread");
        consumerManagerThread.start();
    }

    @Override
    public void stop() {
        for (IConsumer consumer : this.consumerList) {
            try {
                consumer.close();
            } catch (ConsumerException e) {
                logger.log(Level.WARNING, "Occurred Exception while consumer closing." + e);
            }
        }

        isStopSignal = true;
        executor.shutdown();
        pluginManager.shutdown();
    }

    private void process() {
        while (!isStopSignal) {
            List<IConsumer> activeConsumers = this.consumerList.stream().filter(IConsumer::state).collect(Collectors.toList());
            for (IConsumer item : activeConsumers) {
                try {
                    List<IData> data = item.consume();
                    if (!data.isEmpty()) {
                        pluginManager.submit(data);
                    }
                } catch (ConsumerException e) {
                    logger.log(Level.WARNING, "Occurred Exception when fetching or submitting data " + e);
                }
            }
        }
    }
}
