package org.ivanov.notificationservice.service;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;

import java.util.List;

public interface NotificationService {
    void saveMessage(List<CreateMessageDto> dto);

    void sentMessage();
}
