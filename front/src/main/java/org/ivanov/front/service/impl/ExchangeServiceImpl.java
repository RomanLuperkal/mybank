package org.ivanov.front.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.currency.ResponseCurrencyDto;
import org.ivanov.front.client.ExchangeClient;
import org.ivanov.front.service.ExchangeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final ExchangeClient client;

    @Override
    public ResponseCurrencyDto getExchange() {
        return client.getExchange();
    }
}
