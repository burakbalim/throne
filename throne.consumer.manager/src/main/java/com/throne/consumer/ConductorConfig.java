package com.throne.consumer;

import com.throne.plugin.PluginConfig;

import java.util.List;

public class ConductorConfig {

    private List<ConsumerConfig> consumers;
    private List<PluginConfig> plugins;

    public List<ConsumerConfig> getConsumers() {
        return consumers;
    }

    public List<PluginConfig> getPlugins() {
        return plugins;
    }

    public void setConsumers(List<ConsumerConfig> consumers) {
        this.consumers = consumers;
    }

    public void setPlugins(List<PluginConfig> plugins) {
        this.plugins = plugins;
    }
}
