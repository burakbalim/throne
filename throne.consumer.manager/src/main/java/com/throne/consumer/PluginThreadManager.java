package com.throne.consumer;

import throne.orchestration.common.IData;
import throne.orchestration.common.IPlugin;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PluginThreadManager {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    private List<IPlugin> pluginList;
    private static final Object object = new Object();
    private PluginThreadManager instance;

    private PluginThreadManager() {

    }

    public PluginThreadManager getInstance() {
        synchronized (object) {
            if (instance == null) {
                instance = new PluginThreadManager();
            }
        }
        return instance;
    }

    public void submit(IData iData) {
        for (IPlugin  iPlugin : pluginList) {
            threadPoolExecutor.submit(() -> {
                iPlugin.send(iData);
            });
        }
    }

    public void addConsumerPlugin(List<IPlugin> pluginList) {
        this.pluginList = pluginList;
    }
}
