package org.blog.cashservice.mapper;

import org.blog.cashservice.model.NotificationOutBox;
import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;
import org.blog.notificationdto.kafka.event.SaveMessageEvent;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationOutBoxMapper {
    List<CreateMessageDto> mapToMessageDtoList(List<NotificationOutBox> notificationOutBoxList);

    default List<KafkaNotificationEvent> mapToKafkaNotificationEventList(List<NotificationOutBox> notificationOutBoxList) {
        return mapToMessageDtoList(notificationOutBoxList).stream()
                .map(this::toKafkaEvent)
                .toList();
    }

    private KafkaNotificationEvent toKafkaEvent(CreateMessageDto dto) {
        SaveMessageEvent event = new SaveMessageEvent();
        event.setCreateMessageDto(dto);
        return event;
    }
}
