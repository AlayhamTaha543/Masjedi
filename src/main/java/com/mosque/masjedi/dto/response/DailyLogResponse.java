package com.mosque.masjedi.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DailyLogResponse(
        Long id,
        String text,
        LocalDate day,
        Long courseId,
        String courseTitle,
        LocalDateTime createdAt) {
}