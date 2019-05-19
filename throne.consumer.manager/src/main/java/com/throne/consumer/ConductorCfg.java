package com.throne.consumer;

import java.util.List;

public class ConductorCfg {

    private List<ConsumerCfg> consumerCfgList;
    private List<IPluginCfg> pluginList;

    public List<ConsumerCfg> getConsumerCfgList() {
        return consumerCfgList;
    }

    public List<IPluginCfg> getPluginList() {
        return pluginList;
    }

    public void setConsumerCfgList(List<ConsumerCfg> consumerCfgList) {
        this.consumerCfgList = consumerCfgList;
    }

    public void setPluginList(List<IPluginCfg> pluginList) {
        this.pluginList = pluginList;
    }
}
