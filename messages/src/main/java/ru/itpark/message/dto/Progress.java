package ru.itpark.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Progress {

    private UUID id;

    @NotNull
    private Boolean status;

    private UUID lastMessageId;

    private LocalDate createDate;

}
