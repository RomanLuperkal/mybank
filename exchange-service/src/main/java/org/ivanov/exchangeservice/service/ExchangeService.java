package org.ivanov.exchangeservice.service;

import org.ivanov.exchangedto.currency.CreateCurrencyDto;

public interface ExchangeService {
    void changeExchange(CreateCurrencyDto createCurrencyDto);
}
