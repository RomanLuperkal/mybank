package org.ivanov.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.blog.notificationdto.notificationoutbox.CreateMessageDto;
import org.ivanov.notificationservice.mapper.NotificationMapper;
import org.ivanov.notificationservice.model.Notification;
import org.ivanov.notificationservice.repository.NotificationRepository;
import org.ivanov.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String FROM;

    @Override
    @PreAuthorize("hasAuthority('NOTIFICATION_ROLE')")
    public void saveMessage(List<CreateMessageDto> dto) {
        List<Notification> notifications = notificationMapper.mapToNotificationList(dto);
        notificationRepository.saveAll(notifications);
    }

    @Override
    @Transactional
    public void sentMessage() {
        Optional<Notification> notification = notificationRepository
                .findFirstByStatus(Notification.Status.WAITING);
        try {

            if (notification.isPresent()) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(notification.get().getEmail());
                message.setSubject(notification.get().getTheme());
                message.setText(notification.get().getMessage());
                //message.setFrom(FROM);

                javaMailSender.send(message);
                notification.get().setStatus(Notification.Status.SENT);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            notification.ifPresent(notification1 -> notification1.setStatus(Notification.Status.ERROR));
        }

    }
}
