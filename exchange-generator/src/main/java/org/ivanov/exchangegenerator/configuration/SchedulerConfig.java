package org.ivanov.exchangegenerator.configuration;

import org.ivanov.exchangegenerator.client.ExchangeClient;
import org.ivanov.exchangegenerator.scheduling.CurrencyScheduler;
import org.ivanov.exchangegenerator.service.CurrencyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public CurrencyScheduler notificationBoxScheduler(CurrencyService service, ExchangeClient client) {
        return new CurrencyScheduler(service, client);
    }
}
