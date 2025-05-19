package org.ivanov.account.confiruration;

import org.ivanov.account.scheduling.NotificationBoxScheduler;
import org.ivanov.account.service.NotificationOutBoxService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Bean
    public NotificationBoxScheduler notificationBoxScheduler(NotificationOutBoxService service) {
        return new NotificationBoxScheduler(service);
    }
}
