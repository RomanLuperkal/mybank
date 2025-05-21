package org.ivanov.account.service;

import org.ivanov.account.model.NotificationOutBox;

public interface NotificationOutBoxService {
    void sentMessage();

    void createNotificationOutBoxMessage(NotificationOutBox notificationOutBox);

    NotificationOutBox createNotificationOutBoxMessage(String theme, String message, String email);
}
