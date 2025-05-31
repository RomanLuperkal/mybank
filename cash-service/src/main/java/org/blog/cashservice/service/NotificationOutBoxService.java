package org.blog.cashservice.service;

import org.blog.cashservice.model.NotificationOutBox;

public interface NotificationOutBoxService {
    void sentMessage();

    void createNotificationOutBoxMessage(NotificationOutBox notificationOutBox);

    NotificationOutBox prepareNotificationOutBoxMessage(String theme, String message, String email);
}
