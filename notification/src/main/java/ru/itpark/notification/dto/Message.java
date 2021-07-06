package ru.itpark.notification.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Message implements Serializable {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String heading;

    @NotNull
    @NotBlank
    private String text;

}
