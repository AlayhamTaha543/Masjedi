package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LessonRequest(
        @NotBlank String title,
        String description,
        int order,
        @NotNull Long courseId) {
}