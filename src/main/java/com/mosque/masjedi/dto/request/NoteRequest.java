package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NoteRequest(
        @NotBlank String text,
        @NotNull Long studentId) {
}