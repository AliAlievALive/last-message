package ru.itpark.auth.service;

import org.springframework.http.ResponseEntity;
import ru.itpark.auth.dto.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AuthService {

    List<User> getAll();

    ResponseEntity<UUID> register(User user);

    User update(User user);

    String changePassword(UUID userId, String password);

    Map<String, Object> validateToken(String token);

    String login(String login, String password);

    UUID getIdUser(String login);

}
