package com.throne.consumer;

public enum ConsumerType {

    KafkaConsumer("Kafka"), RabbitMqConsumer("Rabbit");

    private final String name;

    ConsumerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
