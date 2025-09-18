package org.ivanov.exchangeservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangedto.currency.ResponseCurrencyDto;
import org.ivanov.exchangeservice.mapper.CurrencyRatesMapper;
import org.ivanov.exchangeservice.model.CurrencyRates;
import org.ivanov.exchangeservice.provider.ExchangeRateProvider;
import org.ivanov.exchangeservice.repository.CurrencyRatesRepository;
import org.ivanov.exchangeservice.service.ExchangeService;
import org.ivanov.exchangeservice.utils.ExchangeUpdateMetrics;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {
    private final ExchangeRateProvider provider;
    private final CurrencyRatesMapper currencyRatesMapper;
    private final CurrencyRatesRepository currencyRatesRepository;
    private final ExchangeUpdateMetrics exchangeUpdateMetrics;

    @Override
    @Transactional
    public void saveExchange(CreateCurrencyDto createCurrencyDto) {
        List<CurrencyRates> currentCurrencies = currencyRatesRepository.findAll();
        currentCurrencies.forEach(c -> updateCurrency(createCurrencyDto, c));
        currencyRatesRepository.saveAll(currentCurrencies);
        currentCurrencies.forEach(this::updateProvider);
        exchangeUpdateMetrics.rateUpdated();
    }

    @Override
    public void changeExchange(CreateCurrencyDto dto) {
        provider.setCNY(dto.cny());
        provider.setUSD(dto.usd());
    }

    @Override
    @PreAuthorize("hasAuthority('EXCHANGE_ROLE')")
    public ResponseCurrencyDto getExchange() {
        return new ResponseCurrencyDto(provider.getUSD(), provider.getCNY());
    }

    private void updateCurrency(CreateCurrencyDto createCurrencyDto, CurrencyRates currencyRates) {
        if (createCurrencyDto.timestamp().isAfter(currencyRates.getTimestamp())) {
            var currenciesMap = currencyRatesMapper.getCurrenciesMap(createCurrencyDto);
            currencyRates.setValue(currenciesMap.get(currencyRates.getCurrencyName()));
            currencyRates.setTimestamp(createCurrencyDto.timestamp());
        }
    }

    private void updateProvider(CurrencyRates currencyRates) {
        switch (currencyRates.getCurrencyName()) {
            case CNY -> provider.setCNY(currencyRates.getValue());
            case USD -> provider.setUSD(currencyRates.getValue());
        }
    }
}
