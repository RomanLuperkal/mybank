package org.ivanov.transfer.mapper;

import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.transfer.model.NotificationOutBox;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationOutBoxMapper {
    List<CreateMessageDto> mapToMessageDtoList(List<NotificationOutBox> notificationOutBoxList);
}
