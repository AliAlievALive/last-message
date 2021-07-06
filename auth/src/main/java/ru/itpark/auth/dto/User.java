package ru.itpark.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@EqualsAndHashCode
public class User {

    private UUID id;

    private String firstName;

    private String lastName;

    private String middleName;

    @NotNull
    @NotBlank
    @Email
    private String login;

    @NotNull
    @NotBlank
    private String password;

    @JsonIgnore
    private Role role;

    private String token;

}
