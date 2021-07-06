package ru.itpark.message.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.itpark.message.dto.LastMessage;
import ru.itpark.message.dto.LastMessageResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/v1/messages")
public interface MessageApi {

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    List<LastMessageResponse> getAll(@RequestHeader("X-Profile") @Valid @NotNull @NotBlank String profile);

    @GetMapping(value = "/{messageId}", produces = APPLICATION_JSON_VALUE)
    LastMessageResponse get(
            @PathVariable @Valid @NotNull UUID messageId,
            @RequestHeader("X-Profile") @Valid @NotNull @NotBlank String profile);

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    UUID create(
            @RequestBody @Valid @NotNull LastMessage lastMessage,
            @RequestHeader("X-Profile") @Valid @NotNull @NotBlank String profile);

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    void update(
            @RequestBody @Valid @NotNull LastMessage lastMessage,
            @RequestHeader("X-Profile") @Valid @NotNull @NotBlank String profile);

    @DeleteMapping("/{messageId}")
    void delete(
            @PathVariable @Valid @NotNull UUID messageId,
            @RequestHeader("X-Profile") @Valid @NotNull @NotBlank String profile);

}
