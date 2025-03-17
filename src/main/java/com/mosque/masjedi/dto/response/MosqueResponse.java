package com.mosque.masjedi.dto.response;

import java.time.LocalDateTime;

public record MosqueResponse(
        Long id,
        String title,
        String description,
        String location,
        LocalDateTime createdAt,
        int numberOfCircles) {
}