package ru.itpark.notification.service;

import ru.itpark.notification.dto.Message;

public interface NotificationService {

    void notify(Message message);

}
