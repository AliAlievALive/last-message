package ru.itpark.message.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.itpark.message.dto.Progress;
import ru.itpark.message.service.LastMessageService;
import ru.itpark.message.service.ProgressService;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledMessageSend {

    private final LastMessageService messageService;
    private final ProgressService progressService;

    @Scheduled(cron = "0 1 0 * * ?")
    public void scheduleMessageSendForAutomaticCreateProgress() {
        var currentDate = LocalDate.now();
        messageService.getAllMessages()
                .stream()
                .filter(message -> message.getProgress().stream()
                        .noneMatch(progress -> progress.getCreateDate().isEqual(currentDate.minusDays(1))))
                .forEach(message -> progressService.createProgress(Progress.builder()
                        .status(false)
                        .lastMessageId(message.getId())
                        .createDate(currentDate.minusDays(1))
                        .build()));
    }
}
