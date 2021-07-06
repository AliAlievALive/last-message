package ru.itpark.message.service;

import ru.itpark.message.dto.Progress;

import java.util.UUID;

public interface ProgressService {

    UUID createProgress(Progress progress);

}
