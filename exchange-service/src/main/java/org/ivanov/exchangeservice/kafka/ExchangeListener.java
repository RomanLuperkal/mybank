package org.ivanov.exchangeservice.kafka;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.kafka.event.KafkaExchangeEvent;
import org.ivanov.exchangeservice.handler.ExchangeEventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ExchangeListener {
    private final Set<ExchangeEventHandler<KafkaExchangeEvent>> handlers;

    @KafkaListener(topics = "exchange", containerFactory = "kafkaListenerContainerFactory")
    public void saveMessage(KafkaExchangeEvent message, Acknowledgment ack) {
        handlers.stream().filter(h -> h.canHandle(message)).findFirst()
                .orElseThrow(() -> new RuntimeException("Обработчик для события не найден"))
                .handleEvent(message);
        ack.acknowledge();
    }
}
