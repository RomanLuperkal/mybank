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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    }

    @Override
    @Transactional
    public void sentMessage() {
        try {
            Optional<Notification> notification = notificationRepository
                    .findNotificationWithStatusWaiting();
            if (notification.isPresent()) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(notification.get().getEmail());
                message.setSubject(notification.get().getTheme());
                message.setText(notification.get().getMessage());
                message.setFrom("dik93@bk.ru");

                javaMailSender.send(message);
            }
        } catch (Exception e) {
            System.out.println();
        }

    }
}
