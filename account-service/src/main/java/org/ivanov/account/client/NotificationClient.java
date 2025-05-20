package org.ivanov.account.client;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;

import java.util.List;

public interface NotificationClient {
    void sentMessage(List<CreateMessageDto> dto);

}
