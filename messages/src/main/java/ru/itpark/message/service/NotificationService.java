package ru.itpark.message.service;

import ru.itpark.message.dto.Notification;

import java.util.UUID;

public interface NotificationService {

    UUID create(Notification notification, UUID userId);

    Notification update(Notification notification, UUID userId);

    void delete(UUID notificationId, UUID userId);

}
