package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CircleRequest(
        @NotBlank String title,
        @NotNull Long mosqueId,
        Long teacherId) {
}