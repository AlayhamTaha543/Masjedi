package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CourseRequest(
        @NotBlank String title,
        String description) {
}