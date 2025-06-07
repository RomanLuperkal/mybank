package org.ivanov.transfer.service.impl;

import lombok.RequiredArgsConstructor;

import org.ivanov.transfer.client.NotificationClient;
import org.ivanov.transfer.mapper.NotificationOutBoxMapper;
import org.ivanov.transfer.model.NotificationOutBox;
import org.ivanov.transfer.repository.NotificationOutBoxRepository;
import org.ivanov.transfer.service.NotificationOutBoxService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationOutBoxServiceImpl implements NotificationOutBoxService {
    private final NotificationClient client;
    private final NotificationOutBoxRepository outboxRepository;
    private final NotificationOutBoxMapper outboxMapper;

    @Override
    @Transactional
    public void sentMessage() {
        List<NotificationOutBox> messages = outboxRepository.getPreparedMessages(PageRequest.of(0, 10));
        if (messages.isEmpty()) {
            return;
        }

        client.sentMessage(outboxMapper.mapToMessageDtoList(messages));
        messages.forEach(m -> m.setStatus(NotificationOutBox.Status.SENT));
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void createNotificationOutBoxMessage(NotificationOutBox notificationOutBox) {
        outboxRepository.save(notificationOutBox);
    }

    @Override
    public NotificationOutBox prepareNotificationOutBoxMessage(String theme, String message, String email) {
        NotificationOutBox notificationOutBox = new NotificationOutBox();
        notificationOutBox.setEmail(email);
        notificationOutBox.setMessage(message);
        notificationOutBox.setTheme(theme);
        return notificationOutBox;
    }
}
