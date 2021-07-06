package ru.itpark.message.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itpark.message.dto.LastMessageResponse;
import ru.itpark.message.dto.LastMessage;
import ru.itpark.message.entity.LastMessageEntity;

@Mapper(uses = {
        NotificationMapper.class,
        ProgressMapper.class
})
public interface LastMessageMapper {

    LastMessageResponse toDto(LastMessageEntity entity);

    @Named("toEntity")
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "progress", ignore = true)
    LastMessageEntity toEntity(LastMessage lastMessage);

    @Named("toExtendedEntity")
    default LastMessageEntity toExtendedEntity(LastMessage lastMessage) {
        LastMessageEntity entity = toEntity(lastMessage);
        entity.getNotifies().forEach(notify -> notify.setMessage(entity));
        return entity;
    }

}
