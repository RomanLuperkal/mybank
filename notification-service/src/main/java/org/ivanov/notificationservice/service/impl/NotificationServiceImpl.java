package org.ivanov.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.notificationservice.mapper.NotificationMapper;
import org.ivanov.notificationservice.model.Notification;
import org.ivanov.notificationservice.repository.NotificationRepository;
import org.ivanov.notificationservice.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public void saveMessage(List<CreateMessageDto> dto) {
        List<Notification> notifications = notificationMapper.mapToNotificationList(dto);
        notificationRepository.saveAll(notifications);
    }
}
