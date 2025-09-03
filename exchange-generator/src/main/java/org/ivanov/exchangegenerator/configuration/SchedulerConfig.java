package org.ivanov.exchangegenerator.configuration;

import org.ivanov.exchangedto.kafka.event.KafkaExchangeEvent;
import org.ivanov.exchangegenerator.scheduling.CurrencyScheduler;
import org.ivanov.exchangegenerator.service.CurrencyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public CurrencyScheduler notificationBoxScheduler(CurrencyService service, KafkaTemplate<String, KafkaExchangeEvent> kafkaTemplate) {
        return new CurrencyScheduler(service,  kafkaTemplate);
    }
}
