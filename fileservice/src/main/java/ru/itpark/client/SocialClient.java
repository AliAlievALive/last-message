package ru.itpark.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itpark.model.dto.MessageFileDto;

import java.util.UUID;

@FeignClient(url = "http://fileservice:8080", name = "file")
public interface SocialClient {
    @GetMapping("/checkAccess")
    boolean checkAccess(@RequestParam UUID fileUUID);

    @GetMapping("/getByShareLink")
    MessageFileDto getFilesharesSocialFile(@RequestParam String shareLink);
}
