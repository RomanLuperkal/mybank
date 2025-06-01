package org.blog.cashservice.configuration;

import lombok.RequiredArgsConstructor;
import org.blog.cashservice.scheduling.TransactionScheduler;
import org.blog.cashservice.service.CashService;
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
}
