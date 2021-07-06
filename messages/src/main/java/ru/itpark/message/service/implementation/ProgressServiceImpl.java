package ru.itpark.message.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itpark.message.dto.Progress;
import ru.itpark.message.mapper.ProgressMapper;
import ru.itpark.message.repository.ProgressRepository;
import ru.itpark.message.service.ProgressService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository repository;
    private final ProgressMapper mapper;

    public UUID createProgress(Progress progress) {
        return repository.save(mapper.toEntityWithmessage(progress)).getId();
    }

}
