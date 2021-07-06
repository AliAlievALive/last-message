package ru.itpark.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LastMessageResponse extends LastMessage {

    private Boolean isActive;

    private List<Progress> progress;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate createDate;

}
