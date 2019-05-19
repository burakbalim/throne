package com.throne.consumer;

import throne.orchestration.common.exception.OrchestractionException;

public class Main {

    public static void main(String[] args) throws OrchestractionException {
        ThroneConsumerService throneConsumerService = ThroneConsumerService.getInstance();

        throneConsumerService.init("/home/burakbalim/configuration/consumer.json");

        throneConsumerService.start();
    }
}

