package org.ivanov.notificationservice.handler;

import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;

import java.util.List;

public interface NotificationEventHandler <T extends KafkaNotificationEvent> {
    boolean canHandle(KafkaNotificationEvent event);

    void handleEvent(T event);

    default void handleBatch(List<T> events) {
        events.forEach(this::handleEvent);
    }
}
