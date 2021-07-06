package ru.itpark.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.itpark.message.dto.enums.NotificationType;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Notification implements Serializable {

    private UUID id;

    private NotificationType notificationType;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime notificationTime = LocalTime.of(8, 0);

    private UUID lastMessageId;

    private String email;

    @JsonIgnore
    private UUID userId;

}
