package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.LessonRequest;
import com.mosque.masjedi.dto.response.LessonResponse;

import java.util.List;

/**
 * Service interface for lesson-related operations.
 */
public interface LessonService {
    // Create operations

    /**
     * Create a new lesson.
     *
     * @param request the lesson request
     * @return the created lesson response
     */
    LessonResponse createLesson(LessonRequest request);

    // Read operations

    /**
     * Get a lesson by ID.
     *
     * @param id the ID of the lesson
     * @return the lesson response
     */
    LessonResponse getLessonById(Long id);

    /**
     * Get lessons by course ID.
     *
     * @param courseId the ID of the course
     * @return the list of lesson responses
     */
    List<LessonResponse> getLessonsByCourseId(Long courseId);

    /**
     * Get lessons by course ID ordered by order ascending.
     *
     * @param courseId the ID of the course
     * @return the list of lesson responses
     */
    List<LessonResponse> getLessonsByCourseIdOrderByOrderAsc(Long courseId);

    /**
     * Get the maximum order by course ID.
     *
     * @param courseId the ID of the course
     * @return the maximum order
     */
    Integer getMaxOrderByCourseId(Long courseId);

    /**
     * Check if a lesson exists in a course.
     *
     * @param courseId the ID of the course
     * @param lessonId the ID of the lesson
     * @return true if the lesson exists in the course, false otherwise
     */
    boolean existsLessonInCourse(Long courseId, Long lessonId);

    // Update operations

    /**
     * Update a lesson.
     *
     * @param id      the ID of the lesson
     * @param request the lesson request
     * @return the updated lesson response
     */
    LessonResponse updateLesson(Long id, LessonRequest request);

    // Delete operations

    /**
     * Delete a lesson by ID.
     *
     * @param id the ID of the lesson
     */
    void deleteLesson(Long id);
}