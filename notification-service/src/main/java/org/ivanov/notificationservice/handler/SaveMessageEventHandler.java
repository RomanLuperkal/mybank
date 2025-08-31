package org.ivanov.notificationservice.handler;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.kafka.enums.NotificationEvent;
import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;
import org.blog.notificationdto.kafka.event.SaveMessageEvent;
import org.ivanov.notificationservice.service.NotificationService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveMessageEventHandler implements NotificationEventHandler<SaveMessageEvent> {
    private final NotificationService notificationService;

    @Override
    public boolean canHandle(KafkaNotificationEvent event) {
        return event.getEvent().equals(NotificationEvent.SAVE_MESSAGE);
    }

    @Override
    public void handleEvent(SaveMessageEvent event) {
        notificationService.saveMessage(event.getCreateMessageDto());
    }
}
