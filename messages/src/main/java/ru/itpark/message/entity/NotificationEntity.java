package ru.itpark.message.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.itpark.message.dto.enums.NotificationType;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@Entity(name = "Notification")
@Data
@EqualsAndHashCode(callSuper = true)
public class NotificationEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @Column
    private LocalTime notificationTime = LocalTime.of(8, 0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_message_id")
    private LastMessageEntity message;

    private String email;

    @Column(nullable = false, updatable = false)
    private UUID userId;

}
