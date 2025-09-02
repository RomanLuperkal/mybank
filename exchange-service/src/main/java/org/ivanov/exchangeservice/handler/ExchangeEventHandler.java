package org.ivanov.exchangeservice.handler;

import org.ivanov.exchangedto.kafka.event.KafkaExchangeEvent;


public interface ExchangeEventHandler <T extends KafkaExchangeEvent> {
    boolean canHandle(KafkaExchangeEvent event);

    void handleEvent(T event);
}