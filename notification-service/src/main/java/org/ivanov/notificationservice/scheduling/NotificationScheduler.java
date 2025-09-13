package org.ivanov.notificationservice.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ivanov.notificationservice.service.NotificationService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Slf4j
public class NotificationScheduler {
    private final NotificationService notificationService;

    @Scheduled(fixedRate  = 1000)
    @Async("taskExecutor")
    public void scheduledSentMessage() {
        notificationService.sentMessage();
    }
}
