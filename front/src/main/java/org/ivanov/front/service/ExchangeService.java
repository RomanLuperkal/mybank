package org.ivanov.front.service;

import org.ivanov.exchangedto.currency.ResponseCurrencyDto;

public interface ExchangeService {
    ResponseCurrencyDto getExchange();
}
