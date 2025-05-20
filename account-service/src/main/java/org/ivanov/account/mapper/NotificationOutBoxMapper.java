package org.ivanov.account.mapper;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.account.model.NotificationOutBox;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationOutBoxMapper {
    List<CreateMessageDto> mapToMessageDtoList(List<NotificationOutBox> notificationOutBoxList);
}
