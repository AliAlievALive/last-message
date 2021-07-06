package ru.itpark.message.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LastMessage implements Serializable {

    private UUID id;

    @NotNull
    private Boolean isNotify;

    private List<Notification> notifies;

    private MessageForNears messageForNears;

    @JsonIgnore
    private UUID userId;

}
