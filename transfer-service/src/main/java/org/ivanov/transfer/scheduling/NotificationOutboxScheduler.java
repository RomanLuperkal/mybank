package org.ivanov.transfer.scheduling;

import lombok.RequiredArgsConstructor;

import org.ivanov.transfer.service.NotificationOutBoxService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class NotificationOutboxScheduler {
    private final NotificationOutBoxService notificationOutBoxService;

    @Scheduled(fixedRate = 5000)
    public void sentMessage() {
        notificationOutBoxService.sentMessage();
    }
}
