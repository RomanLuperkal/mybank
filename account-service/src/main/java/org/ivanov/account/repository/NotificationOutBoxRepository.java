package org.ivanov.account.repository;

import org.ivanov.account.model.NotificationOutBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationOutBoxRepository extends JpaRepository<NotificationOutBox, Long> {
}
