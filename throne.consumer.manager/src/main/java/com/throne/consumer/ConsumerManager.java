package com.throne.consumer;

import throne.orchestration.common.IConsumer;
import throne.orchestration.common.IPlugin;
import throne.orchestration.common.exception.FeederException;
import throne.orchestration.common.manager.OrchestractionManager;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConsumerManager implements OrchestractionManager {

    private List<IPlugin> pluginList;

    private List<IConsumer> consumerList;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private boolean isStopSignal;

    @Override
    public void start() throws FeederException {

    }

    @Override
    public void stop() throws FeederException, FeederException {

    }

    private void onConsume() {

    }
}
