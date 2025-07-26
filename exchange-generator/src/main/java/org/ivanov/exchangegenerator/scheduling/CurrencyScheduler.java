package org.ivanov.exchangegenerator.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangegenerator.client.ExchangeClient;
import org.ivanov.exchangegenerator.service.CurrencyService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class CurrencyScheduler {
    private final CurrencyService currencyService;
    private final ExchangeClient client;

    @Scheduled(fixedDelay = 1000)
    public void getCurrency() {
        log.debug("Отправка информации о валюте");
        CreateCurrencyDto dto  = new CreateCurrencyDto(currencyService.getCurrency(), currencyService.getCurrency());
        log.debug("CreateCurrencyDto: {}", dto);
        client.changeExchange(dto);
    }
}
