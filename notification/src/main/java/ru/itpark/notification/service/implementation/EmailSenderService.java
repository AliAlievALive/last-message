package ru.itpark.notification.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.itpark.notification.dto.Message;
import ru.itpark.notification.service.NotificationService;

@RequiredArgsConstructor
@Service
public class EmailSenderService implements NotificationService {

    private final JavaMailSender sender;

    @Value("${email.message.from}")
    private String fromEmail;

    @Async
    @Override
    public void notify(Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(fromEmail);
        mailMessage.setTo(new String[] {message.getEmail()});
        mailMessage.setSubject(message.getHeading());
        mailMessage.setText(message.getText());
        sender.send(mailMessage);
    }

}
