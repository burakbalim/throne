package com.throne.consumer;

import throne.orchestration.common.IData;
import throne.orchestration.common.ILogger;
import throne.orchestration.common.IPlugin;
import throne.orchestration.common.exception.ConsumerException;
import throne.orchestration.common.exception.FeederException;

public class ConsoleDumpPlugin implements IPlugin {

    @Override
    public void send(IData imessage) {
        System.out.println(imessage.getMessage());
    }

    @Override
    public void configure(String path) throws ConsumerException, FeederException {
        //TODO YEAP
    }

    @Override
    public void setILogger(ILogger ILogger) {
        //TODO YEAP
    }
}
