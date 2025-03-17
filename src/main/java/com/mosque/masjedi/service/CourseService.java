package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.CourseRequest;
import com.mosque.masjedi.dto.response.CourseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {
    // Create operations
    CourseResponse createCourse(CourseRequest request);

    // Read operations
    CourseResponse getCourseById(Long id);

    Page<CourseResponse> getAllCourses(Pageable pageable);

    List<CourseResponse> getCoursesByCircleId(Long circleId);

    List<CourseResponse> getCoursesByStudentId(Long studentId);

    Page<CourseResponse> searchCoursesByTitle(String title, Pageable pageable);

    // Update operations
    CourseResponse updateCourse(Long id, CourseRequest request);

    // Delete operations
    void deleteCourse(Long id);
}