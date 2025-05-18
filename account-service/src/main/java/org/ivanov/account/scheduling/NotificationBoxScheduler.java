package org.ivanov.account.scheduling;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

public class NotificationBoxScheduler {

    @Async
    @Scheduled(fixedRate = 5000)
    public void performAsyncScheduledTask() {
        System.out.println(Thread.currentThread().getName() + " выполняет асинхронную задачу по расписанию");
        // Логика асинхронной задачи
    }
}
