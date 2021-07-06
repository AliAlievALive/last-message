package ru.itpark.notification.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itpark.notification.api.NotificationApi;
import ru.itpark.notification.dto.Message;
import ru.itpark.notification.service.NotificationService;

@RequiredArgsConstructor
@RestController
public class NotificationEmailController implements NotificationApi {

    private final NotificationService notificationService;

    @Override
    public void notify(Message message) {
        notificationService.notify(message);
    }

}
