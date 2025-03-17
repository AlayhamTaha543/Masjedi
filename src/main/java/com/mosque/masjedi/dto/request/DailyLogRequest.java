package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record DailyLogRequest(
        @NotBlank String text,
        @NotNull LocalDate day,
        @NotNull Long courseId) {
}