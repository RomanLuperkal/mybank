package org.ivanov.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;
import org.blog.notificationdto.kafka.event.SaveMessageEvent;
import org.ivanov.notificationservice.handler.NotificationEventHandler;
import org.ivanov.notificationservice.handler.SaveMessageEventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final Set<NotificationEventHandler<KafkaNotificationEvent>> handlers;
    private final SaveMessageEventHandler saveMessageEventHandler;

    @KafkaListener(topics = "notification", containerFactory = "kafkaListenerContainerFactory", batch = "true")
    public void saveMessage(List<KafkaNotificationEvent> messages, Acknowledgment ack) {
        List<SaveMessageEvent> saveMessageEvents = messages.stream()
                .filter(event -> event instanceof SaveMessageEvent)
                .map(event -> (SaveMessageEvent) event)
                .collect(Collectors.toList());

        if (!saveMessageEvents.isEmpty()) {
            saveMessageEventHandler.handleBatch(saveMessageEvents);
            messages.removeAll(saveMessageEvents);
        }

        messages.forEach(message -> handlers.stream().filter(h -> h.canHandle(message)).findFirst()
                .orElseThrow(() -> new RuntimeException("Обработчик для события не найден"))
                .handleEvent(message));
        ack.acknowledge();
    }
}
