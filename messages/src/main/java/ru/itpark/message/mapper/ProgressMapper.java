package ru.itpark.message.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.itpark.message.dto.Progress;
import ru.itpark.message.entity.LastMessageEntity;
import ru.itpark.message.entity.ProgressEntity;

@Mapper
public interface ProgressMapper {

    @Mapping(target = "lastMessageId", ignore = true)
    Progress toDto(ProgressEntity entity);

    @Named("toEntity")
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "message", ignore = true)
    ProgressEntity toEntity(Progress dto);

    @Named("toEntityWithDate")
    @Mapping(target = "message", ignore = true)
    ProgressEntity toEntityWithDate(Progress dto);

    @Named("toEntityWithLastMessage")
    default ProgressEntity toEntityWithmessage(Progress dto) {
        ProgressEntity entity = toEntity(dto);
        var message = new LastMessageEntity();
        message.setId(dto.getLastMessageId());
        entity.setMessage(message);
        return entity;
    }

}
