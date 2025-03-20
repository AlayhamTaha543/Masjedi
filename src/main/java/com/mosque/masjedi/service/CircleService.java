package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.CircleRequest;
import com.mosque.masjedi.dto.response.CircleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for circle-related operations.
 */
public interface CircleService {
    // Create operations

    /**
     * Create a new circle.
     *
     * @param request the circle request
     * @return the created circle response
     */
    CircleResponse createCircle(CircleRequest request);

    // Read operations

    /**
     * Get a circle by ID.
     *
     * @param id the ID of the circle
     * @return the circle response
     */
    CircleResponse getCircleById(Long id);

    /**
     * Get circles by mosque ID with pagination.
     *
     * @param mosqueId the ID of the mosque
     * @param pageable the pagination information
     * @return the paginated list of circle responses
     */
    Page<CircleResponse> getCirclesByMosqueId(Long mosqueId, Pageable pageable);

    /**
     * Get circles by teacher ID.
     *
     * @param teacherId the ID of the teacher
     * @return the list of circle responses
     */
    List<CircleResponse> getCirclesByTeacherId(Long teacherId);

    /**
     * Get circles with students with pagination.
     *
     * @param pageable the pagination information
     * @return the paginated list of circle responses
     */
    Page<CircleResponse> getCirclesWithStudents(Pageable pageable);

    /**
     * Count the number of students in a circle.
     *
     * @param circleId the ID of the circle
     * @return the number of students
     */
    Long countStudentsByCircleId(Long circleId);

    /**
     * Get circles without a teacher by mosque ID.
     *
     * @param mosqueId the ID of the mosque
     * @return the list of circle responses
     */
    List<CircleResponse> getCirclesWithoutTeacherByMosqueId(Long mosqueId);

    // Update operations

    /**
     * Update a circle.
     *
     * @param id      the ID of the circle
     * @param request the circle request
     * @return the updated circle response
     */
    CircleResponse updateCircle(Long id, CircleRequest request);

    /**
     * Assign a teacher to a circle.
     *
     * @param circleId  the ID of the circle
     * @param teacherId the ID of the teacher
     */
    void assignTeacherToCircle(Long circleId, Long teacherId);

    /**
     * Remove a teacher from a circle.
     *
     * @param circleId the ID of the circle
     */
    void removeTeacherFromCircle(Long circleId);

    // Delete operations

    /**
     * Delete a circle by ID.
     *
     * @param id the ID of the circle
     */
    void deleteCircle(Long id);

    // Course management in circles

    /**
     * Add a course to a circle.
     *
     * @param circleId the ID of the circle
     * @param courseId the ID of the course
     */
    void addCourseToCircle(Long circleId, Long courseId);

    /**
     * Remove a course from a circle.
     *
     * @param circleId the ID of the circle
     * @param courseId the ID of the course
     */
    void removeCourseFromCircle(Long circleId, Long courseId);
}