package ru.itpark.message.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itpark.message.dto.Notification;
import ru.itpark.message.entity.LastMessageEntity;
import ru.itpark.message.entity.NotificationEntity;

@Mapper
public interface NotificationMapper {

    @Mapping(target = "lastMessageId", ignore = true)
    Notification toDto(NotificationEntity entity);

    @Named("toEntity")
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "message", ignore = true)
    NotificationEntity toEntity(Notification dto);

    @Named("toEntityWithLastMessage")
    default NotificationEntity toEntityWithLastMessage(Notification dto) {
        NotificationEntity entity = toEntity(dto);
        var message = new LastMessageEntity();
        message.setId(dto.getLastMessageId());
        entity.setMessage(message);
        return entity;
    }



}
