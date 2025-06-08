package org.ivanov.transfer.client;

import org.ivanov.exchangedto.currency.ResponseCurrencyDto;

public interface ExchangeClient {
    ResponseCurrencyDto getExchange();
}
