package ru.itpark.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.message.entity.LastMessageEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LastMessageRepository extends JpaRepository<LastMessageEntity, UUID> {

    @Transactional
    Optional<LastMessageEntity> findByIdAndUserId(UUID uuid, UUID userId);

    @Transactional
    List<LastMessageEntity> findAllByUserId(UUID userId);

}
