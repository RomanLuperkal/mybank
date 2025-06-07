package org.ivanov.transfer.service;


import org.ivanov.transfer.model.NotificationOutBox;

public interface NotificationOutBoxService {
    void sentMessage();

    void createNotificationOutBoxMessage(NotificationOutBox notificationOutBox);

    NotificationOutBox prepareNotificationOutBoxMessage(String theme, String message, String email);
}
