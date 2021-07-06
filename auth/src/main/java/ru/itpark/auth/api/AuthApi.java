package ru.itpark.auth.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itpark.auth.dto.User;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/v1/auth")
public interface AuthApi {

    @GetMapping(value = "/users", produces = APPLICATION_JSON_VALUE)
    List<User> getAll();

    @PostMapping(value = "/users", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> register(@RequestBody @Valid @NotNull @NotBlank User user);

    @PutMapping(value = "/users", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    User update(@RequestBody @Valid @NotNull @NotBlank User user);

    @PatchMapping(value = "/users/{userId}/password")
    String changePassword(@PathVariable("userId") @Valid @NotNull @NotBlank UUID userId,
                          @Valid @NotNull @NotBlank @RequestBody String password);

    @GetMapping
    ResponseEntity validateToken(@RequestHeader("Authorization") String token);

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<User> login(@RequestBody @Valid @NotNull @NotBlank User user);

}
