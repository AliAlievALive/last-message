package ru.itpark.notification.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.itpark.notification.dto.Message;

import static ru.itpark.notification.config.AppConfig.QUEUE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = QUEUE_NAME)
public class Receiver {

    private final EmailSenderService emailSenderService;

    @SneakyThrows
    @RabbitHandler
    public void receiveMessage(Message message) {
        log.info("Before send message");
        emailSenderService.notify(message);
        log.info("After send message");
    }

}
