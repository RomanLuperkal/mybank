package org.ivanov.front.client;

import org.ivanov.exchangedto.currency.ResponseCurrencyDto;

public interface ExchangeClient {
    ResponseCurrencyDto getExchange();
}
