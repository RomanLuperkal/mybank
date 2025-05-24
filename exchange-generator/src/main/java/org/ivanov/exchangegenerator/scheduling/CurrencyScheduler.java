package org.ivanov.exchangegenerator.scheduling;

import lombok.RequiredArgsConstructor;
import org.ivanov.exchangegenerator.service.CurrencyService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class CurrencyScheduler {
    private final CurrencyService currencyService;

    @Scheduled(fixedDelay = 1000)
    public void getCurrency() {
        System.out.println(currencyService.getCurrency());
    }
}
