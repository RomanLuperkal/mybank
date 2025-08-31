package org.ivanov.notificationservice.kafka;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;
import org.ivanov.notificationservice.handler.NotificationEventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final Set<NotificationEventHandler<KafkaNotificationEvent>> handlers;

    @KafkaListener(topics = "notification", containerFactory = "kafkaListenerContainerFactory", batch = "true")
    public void saveMessage(List<KafkaNotificationEvent> messages, Acknowledgment ack) {
        messages.forEach(message -> {
            handlers.stream().filter(h -> h.canHandle(message)).findFirst()
                    .orElseThrow(() -> new RuntimeException("Обработчик для события не найден"))
                    .handleEvent(message);
        });
        ack.acknowledge();
    }
}
