package ru.itpark.message.api;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.message.dto.Notification;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/files")
public interface FileApi {

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    UUID create(@RequestBody MultipartFile multipartFile, @Valid UUID messageId) throws IOException;

    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    UUID update(@RequestBody MultipartFile multipartFile);

    @DeleteMapping("/{messageId}")
    void delete(@PathVariable @Valid @NotNull UUID messageId);
}
