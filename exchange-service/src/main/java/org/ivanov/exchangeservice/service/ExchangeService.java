package org.ivanov.exchangeservice.service;

import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangedto.currency.ResponseCurrencyDto;

public interface ExchangeService {
    void changeExchange(CreateCurrencyDto createCurrencyDto);

    ResponseCurrencyDto getExchange();

    void saveExchange(CreateCurrencyDto createCurrencyDto);
}
