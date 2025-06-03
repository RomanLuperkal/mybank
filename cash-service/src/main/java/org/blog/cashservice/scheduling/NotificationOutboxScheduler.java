package org.blog.cashservice.scheduling;

import lombok.RequiredArgsConstructor;
import org.blog.cashservice.service.NotificationOutBoxService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class NotificationOutboxScheduler {
    private final NotificationOutBoxService notificationOutBoxService;

    @Scheduled(fixedRate = 5000)
    public void sentMessage() {
        notificationOutBoxService.sentMessage();
    }
}
