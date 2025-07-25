package org.ivanov.exchangeservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangedto.currency.ResponseCurrencyDto;
import org.ivanov.exchangeservice.provider.ExchangeRateProvider;
import org.ivanov.exchangeservice.service.ExchangeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('EXCHANGE_ROLE')")
public class ExchangeServiceImpl implements ExchangeService {
    private final ExchangeRateProvider provider;

    @Override
    public void changeExchange(CreateCurrencyDto dto) {
        provider.setCNY(dto.cny());
        provider.setUSD(dto.usd());
    }

    @Override
    public ResponseCurrencyDto getExchange() {
        return new ResponseCurrencyDto(provider.getUSD(), provider.getCNY());
    }
}
