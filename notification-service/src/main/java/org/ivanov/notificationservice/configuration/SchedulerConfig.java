package org.ivanov.notificationservice.configuration;

import org.ivanov.notificationservice.scheduling.NotificationScheduler;
import org.ivanov.notificationservice.service.NotificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableScheduling
@EnableAsync
public class SchedulerConfig {

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("AsyncTask-");
        executor.initialize();
        return executor;
    }

    @Bean
    public NotificationScheduler notificationScheduler(NotificationService notificationService) {
        return new NotificationScheduler(notificationService);
    }
}
