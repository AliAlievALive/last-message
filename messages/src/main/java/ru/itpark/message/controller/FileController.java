package ru.itpark.message.controller;

import kotlin.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.itpark.message.api.FileApi;
import ru.itpark.message.service.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class FileController implements FileApi {

    private final FileService service;

    @Override
    public UUID create(MultipartFile multipartFile, UUID messageId) throws IOException {
        return service.create(multipartFile, messageId);
    }

    @GetMapping(value = "/getFile")
    public ResponseEntity<Resource> getFile(@RequestParam UUID fileUUID) throws IOException {

        Pair<String, File> fileServiceFile = service.getFile(fileUUID);
        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(fileServiceFile.component2()));

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileServiceFile.component1() + "\"")
                .body(inputStreamResource);
    }

    @Override
    public UUID update(MultipartFile multipartFile) {
        return service.update(multipartFile);
    }

    @Override
    public void delete(UUID messageId) {
        service.delete(messageId);
    }

}
