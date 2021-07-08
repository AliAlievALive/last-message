package ru.itpark.service;

import kotlin.Pair;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.model.entity.MessageFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface FileService {
    UUID upload(MultipartFile multipartFile, UUID messageId) throws IOException;

    boolean checkOwnerMessage(List<UUID> fileUUID, UUID messageId);

    List<MessageFile> getAllOwnerFiles(UUID messageId);

    Pair<String, File> getFile(UUID fileUUID);

    Pair<String, File> getFile(UUID fileUUID, UUID messageId);

    Pair<String, File> getFile(String shareLink, UUID messageId);

    void deleteFile(List<UUID> fileUUID, UUID messageId);
}
