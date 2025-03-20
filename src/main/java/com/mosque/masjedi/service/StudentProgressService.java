package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.StudentProgressRequest;
import com.mosque.masjedi.dto.response.StudentProgressResponse;

import java.util.List;

/**
 * Service interface for student progress-related operations.
 */
public interface StudentProgressService {
    // Create operations

    /**
     * Create a new student progress record.
     *
     * @param request the student progress request
     * @return the created student progress response
     */
    StudentProgressResponse createStudentProgress(StudentProgressRequest request);

    // Read operations

    /**
     * Get a student progress record by ID.
     *
     * @param id the ID of the student progress record
     * @return the student progress response
     */
    StudentProgressResponse getStudentProgressById(Long id);

    /**
     * Get student progress records by student and course.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @return the list of student progress responses
     */
    List<StudentProgressResponse> getStudentProgressByStudentAndCourse(Long studentId, Long courseId);

    /**
     * Get completed lessons by student and course.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @return the list of completed lesson responses
     */
    List<StudentProgressResponse> getCompletedLessonsByStudentAndCourse(Long studentId, Long courseId);

    /**
     * Count completed lessons by student and course.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @return the number of completed lessons
     */
    Long countCompletedLessonsByStudentAndCourse(Long studentId, Long courseId);

    // Update operations

    /**
     * Update a student progress record.
     *
     * @param id      the ID of the student progress record
     * @param request the student progress request
     * @return the updated student progress response
     */
    StudentProgressResponse updateStudentProgress(Long id, StudentProgressRequest request);

    // Delete operations

    /**
     * Delete a student progress record by ID.
     *
     * @param id the ID of the student progress record
     */
    void deleteStudentProgress(Long id);
}