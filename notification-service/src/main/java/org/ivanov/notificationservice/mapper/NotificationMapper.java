package org.ivanov.notificationservice.mapper;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.notificationservice.model.Notification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    List<Notification> mapToNotificationList(List<CreateMessageDto> notificationOutBoxList);
}
