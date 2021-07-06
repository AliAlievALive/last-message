package ru.itpark.message.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itpark.message.dto.LastMessage;
import ru.itpark.message.dto.LastMessageResponse;
import ru.itpark.message.dto.MessageForNears;
import ru.itpark.message.exception.LastMessageException;
import ru.itpark.message.exception.NotFoundException;
import ru.itpark.message.mapper.LastMessageMapper;
import ru.itpark.message.repository.LastMessageRepository;
import ru.itpark.message.service.LastMessageService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LastMessageServiceImpl implements LastMessageService {

    private final LastMessageRepository repository;
    private final LastMessageMapper mapper;

    @Override
    public List<LastMessageResponse> getAllMessages(UUID userId) {
        return repository.findAllByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LastMessageResponse> getAllMessages() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LastMessageResponse getMessage(UUID uuid, UUID userId) {
        return repository.findByIdAndUserId(uuid, userId)
                .map(mapper::toDto)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public UUID createMessage(LastMessage lastMessage, UUID userId) {
        lastMessage.setUserId(userId);
        Optional.ofNullable(lastMessage.getNotifies()).ifPresent(notifications -> notifications.forEach(n -> {
            n.setUserId(userId);
            n.setLastMessageId(lastMessage.getId());
        }));
        return repository.save(mapper.toExtendedEntity(lastMessage)).getId();
    }

    @Override
    public void update(LastMessage lastMessage, UUID userId) throws NotFoundException {
        if (Objects.isNull(lastMessage) || Objects.isNull(lastMessage.getId())) {
            throw new LastMessageException();
        }
        if (repository.findByIdAndUserId(lastMessage.getId(), userId).isPresent()) {
            Optional.ofNullable(lastMessage.getNotifies()).ifPresent(notifications -> notifications.forEach(n -> n.setUserId(userId)));
            repository.save(mapper.toExtendedEntity(lastMessage));
            return;
        }
        throw new NotFoundException("Not found message for current user");
    }

    @Override
    public void delete(UUID uuid, UUID userId) {
        if (repository.findByIdAndUserId(uuid, userId).isPresent()) {
            repository.deleteById(uuid);
            return;
        }
        throw new NotFoundException("Not found message for current user");
    }

}
