package org.ivanov.notificationservice.service.impl;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final JavaMailSender javaMailSender;
    private final MeterRegistry meterRegistry;

    @Override
    public void saveMessage(List<CreateMessageDto> dto) {
        List<Notification> notifications = notificationMapper.mapToNotificationList(dto);
        notificationRepository.saveAll(notifications);
    }

    @Override
    @Transactional
    public void sentMessage() {
        Optional<Notification> notification = notificationRepository
                .findFirstByStatus(Notification.Status.WAITING);
        String login = null;
        try {

            if (notification.isPresent()) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(notification.get().getEmail());
                message.setSubject(notification.get().getTheme());
                message.setText(notification.get().getMessage());
                login = notification.get().getLogin();

                javaMailSender.send(message);
                notification.get().setStatus(Notification.Status.SENT);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            meterRegistry.counter("notification_send_error_count_by_login", "login", login).increment();
            notification.ifPresent(notification1 -> notification1.setStatus(Notification.Status.ERROR));
        }
    }
}
