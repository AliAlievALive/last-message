package ru.itpark.message.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.itpark.message.entity.LastMessageEntity;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDto implements Serializable {

    private File file;

    private String url;

    private LastMessageEntity messageEntity;

    private UUID messageId;

}
