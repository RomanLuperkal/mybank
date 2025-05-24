package org.ivanov.exchangegenerator.client;

import org.ivanov.exchangedto.currency.CreateCurrencyDto;

public interface ExchangeClient {
    void changeExchange(CreateCurrencyDto dto);
}
