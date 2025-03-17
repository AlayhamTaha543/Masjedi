package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.LessonRequest;
import com.mosque.masjedi.dto.response.LessonResponse;

import java.util.List;

public interface LessonService {
    // Create operations
    LessonResponse createLesson(LessonRequest request);

    // Read operations
    LessonResponse getLessonById(Long id);

    List<LessonResponse> getLessonsByCourseId(Long courseId);

    List<LessonResponse> getLessonsByCourseIdOrderByOrderAsc(Long courseId);

    Integer getMaxOrderByCourseId(Long courseId);

    boolean existsLessonInCourse(Long courseId, Long lessonId);

    // Update operations
    LessonResponse updateLesson(Long id, LessonRequest request);

    // Delete operations
    void deleteLesson(Long id);
}