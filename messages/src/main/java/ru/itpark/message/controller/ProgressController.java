package ru.itpark.message.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itpark.message.api.ProgressApi;
import ru.itpark.message.dto.Progress;
import ru.itpark.message.service.ProgressService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProgressController implements ProgressApi {

    private final ProgressService service;

    @Override
    public UUID createProgress(Progress progress) {
        return service.createProgress(progress);
    }

}
