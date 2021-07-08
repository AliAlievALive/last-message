package ru.itpark.message.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itpark.message.dto.Progress;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/progress")
public interface ProgressApi {

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    UUID createProgress(@RequestBody @Valid @NotNull Progress progress);

}
