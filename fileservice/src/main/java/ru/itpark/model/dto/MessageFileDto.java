package ru.itpark.model.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class MessageFileDto {
    private UUID messageId;
    private UUID messageFileUUID;
}
