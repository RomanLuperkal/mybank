package org.blog.notificationdto.kafka.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.blog.notificationdto.kafka.enums.NotificationEvent;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;

@Getter
@Setter
@EqualsAndHashCode
public class SaveMessageEvent implements KafkaNotificationEvent {

    private CreateMessageDto createMessageDto;

    @Override
    public NotificationEvent getEvent() {
        return NotificationEvent.SAVE_MESSAGE;
    }
}
