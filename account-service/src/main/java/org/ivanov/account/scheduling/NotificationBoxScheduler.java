package org.ivanov.account.scheduling;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.service.NotificationOutBoxService;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
public class NotificationBoxScheduler {
    private final NotificationOutBoxService notificationOutBoxService;

    @Scheduled(fixedRate = 5000)
    public void performAsyncScheduledTask() {
        System.out.println(Thread.currentThread().getName() + " выполняет асинхронную задачу по расписанию");
        notificationOutBoxService.sentMessage();
    }
}
