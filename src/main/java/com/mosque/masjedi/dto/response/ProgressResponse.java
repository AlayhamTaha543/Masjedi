package com.mosque.masjedi.dto.response;

import java.time.LocalDateTime;

public record ProgressResponse(
        Long id,
        Long studentId,
        Long lessonId,
        String lessonTitle,
        boolean isCompleted,
        String notes,
        LocalDateTime updatedAt) {
}