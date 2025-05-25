package org.ivanov.exchangeservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangeservice.provider.ExchangeRateProvider;
import org.ivanov.exchangeservice.service.ExchangeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final ExchangeRateProvider provider;

    @Override
    //TODO реализовать авторизацию на уровне методов
    public void changeExchange(CreateCurrencyDto dto) {
        provider.setCNY(dto.cny());
        provider.setCNY(dto.usd());
    }
}
