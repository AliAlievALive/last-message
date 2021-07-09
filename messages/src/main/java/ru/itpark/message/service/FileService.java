package ru.itpark.message.service;

import kotlin.Pair;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.message.dto.LastMessage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface FileService {
    UUID create(MultipartFile multipartFile, UUID messageId) throws IOException;

    boolean checkOwner(List<UUID> fileUUID, UUID messageId);

    Pair<String, File> getFile(UUID fileUUID);

    Pair<String, File> getFile(UUID fileUUID, LastMessage message);

    Pair<String, File> getFile(String shareLink, LastMessage message);

    void deleteFile(List<UUID> fileUUID, LastMessage message);

    UUID update(MultipartFile multipartFile);

    void delete(UUID messageId);
}
