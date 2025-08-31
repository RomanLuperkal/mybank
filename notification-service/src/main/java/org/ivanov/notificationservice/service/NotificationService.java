package org.ivanov.notificationservice.service;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;

public interface NotificationService {
    void saveMessage();

    void sentMessage();

    void saveMessage(CreateMessageDto dto);
}
