package ru.itpark.message.service;

import ru.itpark.message.dto.LastMessage;
import ru.itpark.message.dto.LastMessageResponse;

import java.util.List;
import java.util.UUID;

public interface LastMessageService {

    List<LastMessageResponse> getAllMessages(UUID userId);

    List<LastMessageResponse> getAllMessages();

    LastMessageResponse getMessage(UUID uuid, UUID userId);

    UUID createMessage(LastMessage LastMessage, UUID userId);

    void update(LastMessage LastMessage, UUID userId);

    void delete(UUID uuid, UUID userId);

}
