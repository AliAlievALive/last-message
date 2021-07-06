package ru.itpark.message.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class LastMessageUtils {

    public UUID getUserId(String profile) {
        return UUID.fromString(profile.split("_")[0]);
    }

}
