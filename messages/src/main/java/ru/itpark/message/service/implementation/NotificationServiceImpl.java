package ru.itpark.message.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itpark.message.dto.Notification;
import ru.itpark.message.entity.NotificationEntity;
import ru.itpark.message.exception.LastMessageException;
import ru.itpark.message.exception.NotFoundException;
import ru.itpark.message.mapper.NotificationMapper;
import ru.itpark.message.repository.NotificationRepository;
import ru.itpark.message.service.NotificationService;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    @Override
    public UUID create(Notification notification, UUID userId) {
        notification.setUserId(userId);
        return repository.save(mapper.toEntityWithLastMessage(notification)).getId();
    }

    @Override
    public Notification update(Notification notification, UUID userId) {
        if (Objects.isNull(notification) || Objects.isNull(notification.getId())) {
            throw new LastMessageException();
        }
        if (repository.findByIdAndUserId(notification.getId(), userId).isPresent()) {
            NotificationEntity entity = repository.save(mapper.toEntityWithLastMessage(notification));
            return mapper.toDto(entity);
        }
        throw new NotFoundException("Not found notification for current user");
    }

    @Override
    public void delete(UUID notificationId, UUID userId) {
        if (repository.findByIdAndUserId(notificationId, userId).isPresent()) {
            repository.deleteById(notificationId);
            return;
        }
        throw new NotFoundException("Not found notification for current user");
    }

}
