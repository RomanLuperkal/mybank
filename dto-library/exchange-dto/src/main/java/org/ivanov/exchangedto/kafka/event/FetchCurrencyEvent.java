package org.ivanov.exchangedto.kafka.event;

import lombok.Getter;
import lombok.Setter;
import org.ivanov.exchangedto.kafka.enums.ExchangeEvent;

@Getter
@Setter
public class FetchCurrencyEvent implements KafkaExchangeEvent {
    @Override
    public ExchangeEvent getEvent() {
        return ExchangeEvent.FETCH_CURRENCY;
    }
}
