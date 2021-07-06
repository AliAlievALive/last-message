package ru.itpark.auth.service.implementation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itpark.auth.dto.Role;
import ru.itpark.auth.dto.User;
import ru.itpark.auth.entity.UserEntity;
import ru.itpark.auth.exception.AuthException;
import ru.itpark.auth.exception.UserAlreadyExists;
import ru.itpark.auth.mapper.UserMapper;
import ru.itpark.auth.repository.AuthRepository;
import ru.itpark.auth.service.AuthService;
import ru.itpark.auth.service.JwtTokenProvider;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    public static final String BEARER = "Bearer";
    private final AuthRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public List<User> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<UUID> register(User user) {
        repository.findOneByLoginIgnoreCase(user.getLogin())
                .ifPresent(u -> {
                    throw new UserAlreadyExists("This user already exists");
                });
        user.setLogin(user.getLogin()
                .toLowerCase());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UUID id = repository.save(mapper.toEntity(user))
                .getId();
        user.setId(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", jwtTokenProvider.generateToken(user)));
        return new ResponseEntity<>(id, headers, HttpStatus.OK);
    }

    @Override
    public User update(User user) {
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            throw new AuthException();
        }
        user.setLogin(user.getLogin()
                .toLowerCase());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity entity = mapper.toEntity(user);
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public String changePassword(UUID userId, String password) {
        Optional<UserEntity> optional = repository.findById(userId);
        if (optional.isPresent()) {
            UserEntity entity = optional.get();
            entity.setPassword(passwordEncoder.encode(password.replace("\"", StringUtils.EMPTY)));
            return jwtTokenProvider.generateToken(mapper.toDto(entity));
        }
        throw new AuthException("User not found");
    }

    @Override
    public Map<String, Object> validateToken(String token) {
        if (!token.startsWith(BEARER)) {
            throw new AuthException("Invalid authentication scheme found in Authorization header");
        }
        token = token.replace(BEARER, "")
                .trim();
        if (StringUtils.isBlank(token)) {
            throw new AuthException("Authorization header token is empty");
        }
        if (jwtTokenProvider.validateToken(token)) {
            return jwtTokenProvider.parseToken(token)
                    .getClaims();
        }
        throw new AuthException("Incorrect token");
    }

    @Override
    public String login(String login, String password) {
        return repository.findOneByLoginIgnoreCase(login.toLowerCase())
                .map(mapper::toDto)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(jwtTokenProvider::generateToken)
                .orElseThrow(() -> new AuthException("Failed to log in"));
    }

    @Override
    public UUID getIdUser(String login) {
        return repository.findOneByLoginIgnoreCase(login)
                .map(UserEntity::getId)
                .orElse(null);
    }

}
