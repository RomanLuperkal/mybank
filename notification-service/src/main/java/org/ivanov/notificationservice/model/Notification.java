package org.ivanov.notificationservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long messageId;
    private String email;
    private String message;
    private String theme;
    @Enumerated(EnumType.STRING)
    private Status status = Status.WAITING;

    public enum Status {
        WAITING, SENT, ERROR
    }
}
