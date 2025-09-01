package org.ivanov.exchangegenerator.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangedto.kafka.event.KafkaExchangeEvent;
import org.ivanov.exchangegenerator.client.ExchangeClient;
import org.ivanov.exchangegenerator.service.CurrencyService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class CurrencyScheduler {
    private final CurrencyService currencyService;
    private final ExchangeClient client;
    private final KafkaTemplate<String, KafkaExchangeEvent> kafkaTemplate;

    @Scheduled(fixedDelay = 15000)
    @Transactional
    public void getCurrency() {
        log.debug("Отправка информации о валюте");
        //CreateCurrencyDto dto  = new CreateCurrencyDto(currencyService.getCurrency(), currencyService.getCurrency());



        kafkaTemplate.send("exchange", currencyService.getCurrency(), )
        log.debug("CreateCurrencyDto: {}", dto);
        client.changeExchange(dto);
    }
    //TODO доделать отправку одной валюты за раз
    private Message<KafkaExchangeEvent> prepeareMessage(String currency) {
        return null;
        /*return MessageBuilder
                .withPayload(currencyService.getCurrency())  // Объект Order в качестве значения
                .setHeader(KafkaHeaders.TOPIC, "orders")  // Топик
                .setHeader(KafkaHeaders.KEY, currency)  // Ключ - ID заказа
                .setHeader(KafkaHeaders.PARTITION, 1) // Партиция
                .build();*/
    }
}
