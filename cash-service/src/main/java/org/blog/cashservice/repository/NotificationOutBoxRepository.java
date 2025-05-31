package org.blog.cashservice.repository;

import jakarta.persistence.LockModeType;
import org.blog.cashservice.model.NotificationOutBox;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationOutBoxRepository extends JpaRepository<NotificationOutBox, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            SELECT n FROM NotificationOutBox n WHERE n.status = 'WAITING'
            """)
    List<NotificationOutBox> getPreparedMessages(Pageable pageable);
}
