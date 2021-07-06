package ru.itpark.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itpark.auth.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findOneByLoginIgnoreCase(String login);

}
