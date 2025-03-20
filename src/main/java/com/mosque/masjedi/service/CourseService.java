package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.CourseRequest;
import com.mosque.masjedi.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for course-related operations.
 */
public interface CourseService {
    // Create operations

    /**
     * Create a new course.
     *
     * @param request the course request
     * @return the created course response
     */
    CourseResponse createCourse(CourseRequest request);

    // Read operations

    /**
     * Get a course by ID.
     *
     * @param id the ID of the course
     * @return the course response
     */
    CourseResponse getCourseById(Long id);

    /**
     * Get all courses with pagination.
     *
     * @param pageable the pagination information
     * @return the paginated list of course responses
     */
    Page<CourseResponse> getAllCourses(Pageable pageable);

    /**
     * Get courses by circle ID.
     *
     * @param circleId the ID of the circle
     * @return the list of course responses
     */
    List<CourseResponse> getCoursesByCircleId(Long circleId);

    /**
     * Get courses by student ID.
     *
     * @param studentId the ID of the student
     * @return the list of course responses
     */
    List<CourseResponse> getCoursesByStudentId(Long studentId);

    /**
     * Search courses by title with pagination.
     *
     * @param title    the title of the course
     * @param pageable the pagination information
     * @return the paginated list of course responses
     */
    Page<CourseResponse> searchCoursesByTitle(String title, Pageable pageable);

    // Update operations

    /**
     * Update a course.
     *
     * @param id      the ID of the course
     * @param request the course request
     * @return the updated course response
     */
    CourseResponse updateCourse(Long id, CourseRequest request);

    // Delete operations

    /**
     * Delete a course by ID.
     *
     * @param id the ID of the course
     */
    void deleteCourse(Long id);
}