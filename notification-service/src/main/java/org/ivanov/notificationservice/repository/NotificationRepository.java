package org.ivanov.notificationservice.repository;

import jakarta.persistence.LockModeType;
import org.ivanov.notificationservice.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Notification> findFirstByStatus(Notification.Status status);
}
