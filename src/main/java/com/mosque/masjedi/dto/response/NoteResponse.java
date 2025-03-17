package com.mosque.masjedi.dto.response;

import java.time.LocalDateTime;

public record NoteResponse(
        Long id,
        String text,
        Long studentId,
        String studentName,
        Long authorId,
        String authorName,
        LocalDateTime createdAt) {
}