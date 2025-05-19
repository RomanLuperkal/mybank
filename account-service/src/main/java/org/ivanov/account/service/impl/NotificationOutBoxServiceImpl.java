package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.account.client.NotificationClient;
import org.ivanov.account.service.NotificationOutBoxService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationOutBoxServiceImpl implements NotificationOutBoxService {
    private final NotificationClient client;

    @Override
    @Scheduled(fixedDelay = 10000)
    public void sentMessage() {
        client.sentMessage(new CreateMessageDto("test email", "test message"));
    }
}
