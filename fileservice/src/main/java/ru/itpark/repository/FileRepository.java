package ru.itpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.model.entity.MessageFile;

import java.util.List;
import java.util.UUID;

public interface FileRepository extends JpaRepository<MessageFile, UUID> {
    boolean existsByMessageIdAndUuidIn(UUID messageId, List<UUID> fileUUID);

    List<MessageFile> findAllByMessageIdAndDeletedFalse(UUID messageId);

    MessageFile findByUuid(UUID fileUUID);

    List<MessageFile> findByUuidIn(List<UUID> fileUUID);
}