package com.throne.consumer;

import throne.orchestration.common.IData;
import throne.orchestration.common.IPlugin;

import java.util.List;
import java.util.concurrent.*;

class PluginManager {

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private List<IPlugin> pluginList;
    private static final Object object = new Object();
    private static PluginManager instance;

    private PluginManager() {

    }

    static PluginManager getInstance() {
        synchronized (object) {
            if (instance == null) {
                instance = new PluginManager();
            }
        }
        return instance;
    }

    void submit(List<IData> iDatas) {
        for (IPlugin  iPlugin : pluginList) {
            threadPoolExecutor.submit(() -> {
                //TODO Debug log
                iDatas.forEach(iPlugin::send);
            });
        }
    }

    void addConsumerPlugin(List<IPlugin> pluginList) {
        this.pluginList = pluginList;
    }

    public void shutdown() {
        threadPoolExecutor.shutdown();
    }
}
