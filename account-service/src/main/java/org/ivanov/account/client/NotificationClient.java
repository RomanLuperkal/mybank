package org.ivanov.account.client;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;

public interface NotificationClient {
    void sentMessage(CreateMessageDto dto);

}
