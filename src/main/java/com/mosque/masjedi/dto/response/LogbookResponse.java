package com.mosque.masjedi.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record LogbookResponse(
        Long id,
        Long studentId,
        Long courseId,
        String text,
        LocalDate day,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}