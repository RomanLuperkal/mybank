package org.ivanov.notificationservice.handler;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.kafka.enums.NotificationEvent;
import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;
import org.blog.notificationdto.kafka.event.SaveMessageEvent;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.notificationservice.service.NotificationService;
import org.springframework.stereotype.Component;

import java.util.List;

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

    }

    @Override
    public void handleBatch(List<SaveMessageEvent> events) {
        List<CreateMessageDto> list = events.stream().map(SaveMessageEvent::getCreateMessageDto).toList();
        notificationService.saveMessage(list);
    }
}
