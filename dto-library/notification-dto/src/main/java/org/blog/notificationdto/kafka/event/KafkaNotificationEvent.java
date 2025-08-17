package org.blog.notificationdto.kafka.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.blog.notificationdto.kafka.enums.NotificationEvent;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "event"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SaveMessageEvent.class, name = "SAVE_MESSAGE")
})
public interface KafkaNotificationEvent {
    NotificationEvent getEvent();
}
