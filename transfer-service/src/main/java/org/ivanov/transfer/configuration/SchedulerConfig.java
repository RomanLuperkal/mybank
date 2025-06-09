package org.ivanov.transfer.configuration;

import lombok.RequiredArgsConstructor;

import org.ivanov.transfer.scheduling.NotificationOutboxScheduler;
import org.ivanov.transfer.scheduling.TransactionScheduler;
import org.ivanov.transfer.service.NotificationOutBoxService;
import org.ivanov.transfer.service.TransferService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class SchedulerConfig {

    @Bean
    public TransactionScheduler transactionScheduler(TransferService transferService) {
        return new TransactionScheduler(transferService);
    }

    @Bean
    public NotificationOutboxScheduler notificationOutboxScheduler(NotificationOutBoxService notificationService) {
        return new NotificationOutboxScheduler(notificationService);
    }
}
