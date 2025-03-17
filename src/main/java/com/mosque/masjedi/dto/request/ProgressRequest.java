package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotNull;

public record ProgressRequest(
        @NotNull Long studentId,
        @NotNull Long lessonId,
        boolean isCompleted,
        String notes) {
}