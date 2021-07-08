package ru.itpark.controller;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.model.entity.MessageFile;
import ru.itpark.service.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/files")
@RequiredArgsConstructor
@RestController
public class FileController {
    private final FileService fileService;

    @GetMapping
    public List<MessageFile> getAllOwnerFiles(@RequestParam UUID messageId) {
        return fileService.getAllOwnerFiles(messageId);
    }

    @PostMapping("/upload")
    public UUID uploadFile(@RequestParam("file") MultipartFile multipartFile,
                           @RequestParam UUID messageId) throws IOException {
        return fileService.upload(multipartFile, messageId);
    }

    @GetMapping(value = "/getFile")
    public ResponseEntity<Resource> getFile(@RequestParam UUID fileUUID,
                                            @RequestParam UUID messageId) throws IOException {

        Pair<String, File> messageFile = fileService.getFile(fileUUID, messageId);
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(messageFile.component2()));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + messageFile.component1() + "\"")
                .body(inputStreamResource);
    }

    @GetMapping("/getFileByShareLink")
    public ResponseEntity<Resource> getFile(@RequestParam String shareLink,
                                            @RequestParam UUID messageId) throws FileNotFoundException {
        Pair<String, File> fileServiceFile = fileService.getFile(shareLink, messageId);
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(fileServiceFile.component2()));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileServiceFile.component1() + "\"")
                .body(inputStreamResource);
    }

    @PostMapping("/deleteFile")
    public void deleteFile(@RequestParam List<UUID> fileUUID,
                           @RequestParam UUID messageId) {
        fileService.deleteFile(fileUUID, messageId);
    }
}