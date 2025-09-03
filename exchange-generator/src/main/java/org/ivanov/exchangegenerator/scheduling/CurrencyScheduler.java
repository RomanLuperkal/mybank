package org.ivanov.exchangegenerator.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangedto.kafka.event.FetchCurrencyEvent;
import org.ivanov.exchangedto.kafka.event.KafkaExchangeEvent;
import org.ivanov.exchangegenerator.service.CurrencyService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
public class CurrencyScheduler {
    private final CurrencyService currencyService;
    private final KafkaTemplate<String, KafkaExchangeEvent> kafkaTemplate;

    @Scheduled(fixedDelay = 15000)
    public void getCurrency() {
        log.debug("Отправка информации о валюте");
        CreateCurrencyDto dto  = new CreateCurrencyDto(currencyService.getCurrency(), currencyService.getCurrency(),
                LocalDateTime.now());
        KafkaExchangeEvent event = new FetchCurrencyEvent(dto);

        var message = MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, "exchange").build();


        kafkaTemplate.send(message);
        log.debug("CreateCurrencyDto: {}", dto);
    }
}
