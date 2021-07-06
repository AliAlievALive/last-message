package ru.itpark.message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;
import ru.itpark.message.api.MessageApi;
import ru.itpark.message.dto.LastMessage;
import ru.itpark.message.dto.LastMessageResponse;
import ru.itpark.message.service.LastMessageService;
import ru.itpark.message.util.LastMessageUtils;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

    private final LastMessageService service;

    @Override
    @Cacheable(value = "messages", key = "#profile")
    public List<LastMessageResponse> getAll(String profile) {
        return service.getAllMessages(LastMessageUtils.getUserId(profile));
    }

    @Override
    @Cacheable(value = "last_message", key = "#profile")
    public LastMessageResponse get(UUID messageId, String profile) {
        return service.getMessage(messageId, LastMessageUtils.getUserId(profile));
    }

    @Override
    @CacheEvict(value = {"last_message", "messages"}, key = "#profile")
    public UUID create(LastMessage lastMessage, String profile) {
        return service.createMessage(lastMessage, LastMessageUtils.getUserId(profile));
    }

    @Override
    @CacheEvict(value = {"last_message", "messages"}, key = "#profile")
    public void update(LastMessage lastMessage, String profile) {
        service.update(lastMessage, LastMessageUtils.getUserId(profile));
    }

    @Override
    @CacheEvict(value = {"last_message", "messages"}, key = "#profile")
    public void delete(UUID messageId, String profile) {
        service.delete(messageId, LastMessageUtils.getUserId(profile));
    }

}
