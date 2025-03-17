package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MosqueRequest(
        @NotBlank String title,
        String description,
        String location) {
}