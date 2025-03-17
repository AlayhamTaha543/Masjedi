package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record LogbookRequest(
        @NotNull Long studentId,
        @NotNull Long courseId,
        @NotBlank String text,
        @NotNull LocalDate day) {
}