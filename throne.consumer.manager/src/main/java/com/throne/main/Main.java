package com.throne.main;

import com.throne.consumer.ConsumerService;
import throne.orchestration.common.exception.OrchestrationException;

public class Main {

    public static void main(String[] args) throws OrchestrationException {
        ConsumerService consumerService = ConsumerService.getInstance();

        consumerService.init("/Users/burakbalim/codes/throne/throne.consumer.manager/src/main/resources/config/config.json");

        consumerService.start();
    }
}

