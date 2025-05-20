package org.ivanov.account.service;

import org.ivanov.account.model.NotificationOutBox;

public interface NotificationOutBoxService {
    void sentMessage();

    void createNotificationOutBoxMessage(NotificationOutBox notificationOutBox);
}
