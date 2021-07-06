package ru.itpark.message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itpark.message.api.NotificationApi;
import ru.itpark.message.dto.Notification;
import ru.itpark.message.service.NotificationService;
import ru.itpark.message.util.LastMessageUtils;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class NotificationController implements NotificationApi {

    private final NotificationService service;

    @Override
    public UUID create(Notification notification, String profile) {
        return service.create(notification, LastMessageUtils.getUserId(profile));
    }

    @Override
    public Notification update(Notification notification, String profile) {
        return service.update(notification, LastMessageUtils.getUserId(profile));
    }

    @Override
    public void delete(UUID notificationId, String profile) {
        service.delete(notificationId, LastMessageUtils.getUserId(profile));
    }

}
