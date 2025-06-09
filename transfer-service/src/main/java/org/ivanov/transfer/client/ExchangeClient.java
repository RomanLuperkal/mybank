package org.ivanov.transfer.client;

import org.ivanov.exchangedto.currency.ResponseCurrencyDto;

import java.util.concurrent.CompletableFuture;

public interface ExchangeClient {
    CompletableFuture<ResponseCurrencyDto> getExchange();
}
