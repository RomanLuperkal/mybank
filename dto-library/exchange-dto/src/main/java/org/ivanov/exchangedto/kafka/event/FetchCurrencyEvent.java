package org.ivanov.exchangedto.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangedto.kafka.enums.ExchangeEvent;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FetchCurrencyEvent implements KafkaExchangeEvent {
    private CreateCurrencyDto dto;

    @Override
    public ExchangeEvent getEvent() {
        return ExchangeEvent.FETCH_CURRENCY;
    }
}
