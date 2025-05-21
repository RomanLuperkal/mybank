package org.ivanov.notificationservice.service;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.notificationservice.model.Notification;

import java.util.List;

public interface NotificationService {
    void saveMessage(List<CreateMessageDto> dtp);

    void sentMessage(Notification notification);
}
