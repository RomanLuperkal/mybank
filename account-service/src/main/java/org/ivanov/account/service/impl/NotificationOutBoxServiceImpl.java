package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blog.notificationdto.kafka.event.KafkaNotificationEvent;
import org.ivanov.account.client.NotificationClient;
import org.ivanov.account.mapper.NotificationOutBoxMapper;
import org.ivanov.account.model.NotificationOutBox;
import org.ivanov.account.repository.NotificationOutBoxRepository;
import org.ivanov.account.service.NotificationOutBoxService;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationOutBoxServiceImpl implements NotificationOutBoxService {
    private final NotificationClient client;
    private final NotificationOutBoxRepository outboxRepository;
    private final NotificationOutBoxMapper outboxMapper;
    private final KafkaTemplate<String, KafkaNotificationEvent> notificationProducer;

    @Override
    @Transactional
    public void sentMessage() {
        List<NotificationOutBox> messages = outboxRepository.getPreparedMessages(PageRequest.of(0, 10));
        if (messages.isEmpty()) {
            return;
        }

        List<KafkaNotificationEvent> kafkaNotificationEvents = outboxMapper.mapToKafkaNotificationEventList(messages);

        kafkaNotificationEvents.forEach(event -> notificationProducer.send("notification", event)
                .whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Ошибка при отправке сообщения: {}", ex.getMessage(), ex);
            }
        }));


        //client.sentMessage(outboxMapper.mapToMessageDtoList(messages));
        messages.forEach(m -> m.setStatus(NotificationOutBox.Status.SENT));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void createNotificationOutBoxMessage(NotificationOutBox notificationOutBox) {
        outboxRepository.save(notificationOutBox);
    }

    @Override
    public NotificationOutBox createNotificationOutBoxMessage(String theme, String message, String email) {
        NotificationOutBox notificationOutBox = new NotificationOutBox();
        notificationOutBox.setEmail(email);
        notificationOutBox.setMessage(message);
        notificationOutBox.setTheme(theme);
        return notificationOutBox;
    }
}
