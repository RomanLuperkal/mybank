package org.ivanov.notificationservice.handler;

import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;

public interface NotificationEventHandler<T extends KafkaNotificationEvent> {
    boolean canHandle(KafkaNotificationEvent event);

    void handleEvent(T event);
}
