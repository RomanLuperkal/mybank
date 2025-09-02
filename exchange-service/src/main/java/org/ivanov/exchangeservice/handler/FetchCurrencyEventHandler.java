package org.ivanov.exchangeservice.handler;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.kafka.enums.ExchangeEvent;
import org.ivanov.exchangedto.kafka.event.FetchCurrencyEvent;
import org.ivanov.exchangedto.kafka.event.KafkaExchangeEvent;
import org.ivanov.exchangeservice.service.ExchangeService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchCurrencyEventHandler implements ExchangeEventHandler<FetchCurrencyEvent> {
    private final ExchangeService exchangeService;

    @Override
    public boolean canHandle(KafkaExchangeEvent event) {
        return event.getEvent().equals(ExchangeEvent.FETCH_CURRENCY);
    }

    @Override
    public void handleEvent(FetchCurrencyEvent event) {
        exchangeService.saveExchange(event.getDto());
    }
}
