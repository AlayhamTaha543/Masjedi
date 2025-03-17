package com.mosque.masjedi.dto.response;

import java.time.LocalDateTime;

public record CourseResponse(
        Long id,
        String title,
        String description,
        int numberOfLessons,
        LocalDateTime createdAt) {
}