package ru.itpark.service.impl;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.client.SocialClient;
import ru.itpark.model.dto.MessageFileDto;
import ru.itpark.model.entity.MessageFile;
import ru.itpark.repository.FileRepository;
import ru.itpark.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Value("${application.storage-path}")
    private String storagePath;

    private final FileRepository fileRepository;
    private final SocialClient socialClient;
    private final RabbitTemplate template;

    @Override
    public UUID upload(MultipartFile multipartFile, UUID messageId) throws IOException {
        String fileName = RandomStringUtils.random(15, true, true);
        String extension = getExtension(multipartFile.getOriginalFilename());

        String fullFileName = getFullStoragePath() + fileName + "." + extension;
        File file = new File(getFullStoragePath());
        file.mkdirs();

        multipartFile.transferTo(Path.of(fullFileName));

        MessageFile messageFile = new MessageFile();
        messageFile.setOriginalName(multipartFile.getOriginalFilename());
        messageFile.setFileName(fileName);
        messageFile.setPath(getFullStoragePath());
        messageFile.setMessageId(messageId);
        messageFile.setExtension(extension);
        messageFile.setSize(multipartFile.getSize());
        MessageFile save = fileRepository.save(messageFile);

        return save.getUuid();
    }

    private String getFullStoragePath() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        int year = ldt.getYear();
        int month = ldt.getMonthValue();
        int dayOfMonth = ldt.getDayOfMonth();

        return storagePath + year + "/" + month + "/" + dayOfMonth + "/";
    }

    private String getExtension(String fileName) {
        Pattern pattern = Pattern.compile("(\\.[^.]*)$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            return matcher.group(0).substring(1);
        }

        return null;
    }

    @Override
    public boolean checkOwnerMessage(List<UUID> fileId, UUID messageId) {
        return fileRepository.existsByMessageIdAndUuidIn(messageId, fileId);
    }

    @Override
    public List<MessageFile> getAllOwnerFiles(UUID messageId) {
        return fileRepository.findAllByMessageIdAndDeletedFalse(messageId);
    }

    @Override
    public Pair<String, File> getFile(UUID fileUUID) {
        MessageFile messageFile = fileRepository.findByUuid(fileUUID);

        String fullFileName = messageFile.getPath() + messageFile.getFileName() + "." + messageFile.getExtension();
        return new Pair<>(messageFile.getOriginalName(), new File(fullFileName));
    }

    @Override
    public Pair<String, File> getFile(UUID fileUUID, UUID messageId) {
        MessageFile messageFile = fileRepository.findByUuid(fileUUID);

        if (messageFile.getMessageId().equals(messageId) || socialClient.checkAccess(fileUUID)) {
            String fullFileName = messageFile.getPath() + messageFile.getFileName() + "." + messageFile.getExtension();
            return new Pair<>(messageFile.getOriginalName(), new File(fullFileName));
        }

        throw new RuntimeException("");
    }

    @Override
    public Pair<String, File> getFile(String shareLink, UUID messageId) {
        MessageFileDto messageFileDto = socialClient.getFilesharesSocialFile(shareLink);

        if (messageFileDto != null) {
            MessageFile messageFile = fileRepository.findByUuid(messageFileDto.getMessageFileUUID());
            String fullFileName = messageFile.getPath() + messageFile.getFileName() + "." + messageFile.getExtension();
            return new Pair<>(messageFile.getOriginalName(), new File(fullFileName));
        }

        return null;
    }

    @Override
    public void deleteFile(List<UUID> fileUUID, UUID messageId) {
        List<MessageFile> messageFiles = fileRepository.findByUuidIn(fileUUID);
        boolean isAnyNotOwnedFile = messageFiles.stream().anyMatch(messageFile -> !messageFile.getMessageId().equals(messageId));
        if (isAnyNotOwnedFile) {
            return;
        }

        List<UUID> uuidsForDelete = new ArrayList<>();
        for (MessageFile messageFile : messageFiles) {
            messageFile.setDeleted(true);

            File file = new File(messageFile.getPath() + messageFile.getFileName() + "." + messageFile.getExtension());
            if (file.delete()) {
                uuidsForDelete.add(messageFile.getUuid());
            }
        }
        fileRepository.saveAll(messageFiles);
        template.convertAndSend("", "files-deleted-social-queue", uuidsForDelete);
        template.convertAndSend("", "files-deleted-archiver-queue", uuidsForDelete);
    }
}