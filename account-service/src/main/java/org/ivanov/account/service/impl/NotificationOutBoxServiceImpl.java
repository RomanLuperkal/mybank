package org.ivanov.account.service.impl;

import lombok.RequiredArgsConstructor;
import org.ivanov.account.client.NotificationClient;
import org.ivanov.account.mapper.NotificationOutBoxMapper;
import org.ivanov.account.model.NotificationOutBox;
import org.ivanov.account.repository.NotificationOutBoxRepository;
import org.ivanov.account.service.NotificationOutBoxService;
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
        client.sentMessage(outboxMapper.mapToMessageDtoList(messages));
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
