package org.ivanov.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.notificationservice.mapper.NotificationMapper;
import org.ivanov.notificationservice.model.Notification;
import org.ivanov.notificationservice.repository.NotificationRepository;
import org.ivanov.notificationservice.service.NotificationService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender javaMailSender;

    @Override
    public void saveMessage(List<CreateMessageDto> dto) {
        List<Notification> notifications = notificationMapper.mapToNotificationList(dto);
        notificationRepository.saveAll(notifications);
        sentMessage(notifications.getFirst());
    }

    @Override
    public void sentMessage(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getEmail());
        message.setSubject(notification.getTheme());
        message.setText(notification.getMessage());
        message.setFrom("dik93@bk.ru");

        javaMailSender.send(message);
    }
}
