package com.throne.plugin;

import throne.orchestration.common.IData;
import throne.orchestration.common.IPlugin;

public class ConsoleDumpPlugin implements IPlugin {

    @Override
    public void send(IData imessage) {
        System.out.println(imessage.getMessage());
    }

    @Override
    public void configure(String path) {
    }
}
