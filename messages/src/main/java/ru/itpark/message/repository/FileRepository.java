package ru.itpark.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {

    @Transactional
    Optional<FileEntity> findByIdAndMessageId(UUID uuid, UUID messageId);
}
