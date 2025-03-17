package com.mosque.masjedi.dto.response;

import java.time.LocalDateTime;

public record CircleResponse(
        Long id,
        String title,
        Long mosqueId,
        String mosqueTitle,
        Long teacherId,
        String teacherName,
        int numberOfStudents,
        int numberOfCourses,
        LocalDateTime createdAt) {
}