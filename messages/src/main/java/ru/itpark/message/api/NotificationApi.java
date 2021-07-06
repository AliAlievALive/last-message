package ru.itpark.message.api;

import org.springframework.web.bind.annotation.*;
import ru.itpark.message.dto.Notification;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/v1/notifications")
public interface NotificationApi {

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    UUID create(@RequestBody @Valid @NotNull Notification notification,
                @RequestHeader("X-Profile") @Valid @NotNull @NotBlank String profile);

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    Notification update(@RequestBody @Valid @NotNull Notification notification,
                        @RequestHeader("X-Profile") @Valid @NotNull @NotBlank String profile);

    @DeleteMapping("/{notificationId}")
    void delete(@PathVariable @Valid @NotNull UUID notificationId,
                @RequestHeader("X-Profile") @Valid @NotNull @NotBlank String profile);

}
