package org.ivanov.exchangeservice.mapper;

import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangeservice.model.CurrencyRates;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface CurrencyRatesMapper {
    default Map<CurrencyRates.Currencies, BigDecimal> getCurrenciesMap(CreateCurrencyDto createCurrencyDto) {
        Map<CurrencyRates.Currencies, BigDecimal> currenciesMap = new HashMap<>();
        currenciesMap.put(CurrencyRates.Currencies.CNY, createCurrencyDto.cny());
        currenciesMap.put(CurrencyRates.Currencies.USD, createCurrencyDto.usd());
        return currenciesMap;
    }
}
