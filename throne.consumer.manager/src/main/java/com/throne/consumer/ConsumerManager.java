package com.throne.consumer;

import throne.orchestration.common.IConsumer;
import throne.orchestration.common.IData;
import throne.orchestration.common.IPlugin;
import throne.orchestration.common.exception.ConsumerException;
import throne.orchestration.common.manager.OrchestractionManager;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class ConsumerManager implements OrchestractionManager {

    private final static Object object = new Object();

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private PluginManager pluginManager = PluginManager.getInstance();

    private List<IConsumer> consumerList;

    private boolean isStopSignal;

    public void init(List<IConsumer> consumerList, List<IPlugin> plugins) {
        this.consumerList = consumerList;
        this.pluginManager.addConsumerPlugin(plugins);
    }

    @Override
    public void start() {
        this.consumerList.forEach(consumer -> {
            try {
                //TODO log
                consumer.open();
            } catch (ConsumerException e) {
                //log
            }
        });

        isStopSignal = false;
        Thread consumerManagerThread = new Thread(this::process, "Consumer Manager Main-Thread");
        consumerManagerThread.start();
    }

    @Override
    public void stop() {
        this.consumerList.forEach(consumer -> {
            try {
                //TODO log
                consumer.close();
            } catch (ConsumerException e) {
                //TODO log
            }
        });

        isStopSignal = true;
        threadPoolExecutor.shutdown();
        pluginManager.shutdown();
    }

    private void process() {
        while (!isStopSignal) {
            List<IConsumer> activeConsumers = this.consumerList.stream().filter(IConsumer::state).collect(Collectors.toList());
            for (IConsumer item : activeConsumers) {
                threadPoolExecutor.submit(() -> {
                    try {
                        List<IData> consume = item.consume();
                        if (!consume.isEmpty()) {
                            pluginManager.submit(consume);
                        }
                    } catch (ConsumerException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
