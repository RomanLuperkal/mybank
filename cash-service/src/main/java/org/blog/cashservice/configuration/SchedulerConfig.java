package org.blog.cashservice.configuration;

import lombok.RequiredArgsConstructor;
import org.blog.cashservice.scheduling.NotificationOutboxScheduler;
import org.blog.cashservice.scheduling.TransactionScheduler;
import org.blog.cashservice.service.CashService;
import org.blog.cashservice.service.NotificationOutBoxService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    @Bean
    public TransactionScheduler transactionScheduler(CashService cashService) {
        return new TransactionScheduler(cashService);
    }

    @Bean
    public NotificationOutboxScheduler notificationOutboxScheduler(NotificationOutBoxService notificationService) {
        return new NotificationOutboxScheduler(notificationService);
    }
}
