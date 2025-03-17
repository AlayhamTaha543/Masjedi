package com.mosque.masjedi.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class StudentProgressResponse {
    private Long id;
    private Long studentId;
    private Long lessonId;
    private String lessonTitle;
    private boolean isCompleted;
    private String notes;
    private LocalDateTime updatedAt;
}