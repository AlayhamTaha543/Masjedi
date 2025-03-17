package com.mosque.masjedi.dto.response;

import java.time.LocalDateTime;

public record LessonResponse(
                Long id,
                String title,
                String description,
                int order,
                Long courseId,
                LocalDateTime createdAt) {
}