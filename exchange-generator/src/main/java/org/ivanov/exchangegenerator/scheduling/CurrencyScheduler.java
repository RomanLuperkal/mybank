package org.ivanov.exchangegenerator.scheduling;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangedto.currency.CreateCurrencyDto;
import org.ivanov.exchangegenerator.client.ExchangeClient;
import org.ivanov.exchangegenerator.service.CurrencyService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class CurrencyScheduler {
    private final CurrencyService currencyService;
    private final ExchangeClient client;

    @Scheduled(fixedDelay = 1000)
    public void getCurrency() {
        CreateCurrencyDto dto  = new CreateCurrencyDto(currencyService.getCurrency(), currencyService.getCurrency());
        client.changeExchange(dto);
    }
}
