package ru.itpark.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.message.entity.ProgressEntity;

import java.util.UUID;

public interface ProgressRepository extends JpaRepository<ProgressEntity, UUID> {
}
