package org.blog.cashservice.mapper;

import org.blog.cashservice.model.NotificationOutBox;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationOutBoxMapper {
    List<CreateMessageDto> mapToMessageDtoList(List<NotificationOutBox> notificationOutBoxList);
}
