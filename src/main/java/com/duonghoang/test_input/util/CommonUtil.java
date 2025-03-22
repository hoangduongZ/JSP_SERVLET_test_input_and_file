package com.duonghoang.test_input.util;


import jakarta.servlet.http.Part;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class CommonUtil {
    public static <T> T convertToLocalDate(Date date, Class<T> type){
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());

        if (type.equals(LocalDate.class)) {
            return type.cast(zdt.toLocalDate());
        } else if (type.equals(LocalDateTime.class)) {
            return type.cast(zdt.toLocalDateTime());
        }else if (type.equals(LocalTime.class)){
            return type.cast(zdt.toLocalTime());
        }

        throw new IllegalArgumentException("Unsupported type: " + type.getSimpleName());
    }

    public static boolean checkTypeFile(Part filePart, String... allowedTypes) {
        if (filePart == null || filePart.getContentType() == null) {
            return false;
        }

        String contentType = filePart.getContentType().toLowerCase();
        for (String type : allowedTypes) {
            if (contentType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }
}
