package org.ivanov.account.scheduling;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.service.NotificationOutBoxService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class NotificationBoxScheduler {
    private final NotificationOutBoxService notificationOutBoxService;

    @Scheduled(fixedDelay = 10000)
    public void performAsyncScheduledTask() {
        notificationOutBoxService.sentMessage();
    }
}
