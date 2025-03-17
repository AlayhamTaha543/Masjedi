package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.CircleRequest;
import com.mosque.masjedi.dto.response.CircleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CircleService {
    // Create operations
    CircleResponse createCircle(CircleRequest request);

    // Read operations
    CircleResponse getCircleById(Long id);

    Page<CircleResponse> getCirclesByMosqueId(Long mosqueId, Pageable pageable);

    List<CircleResponse> getCirclesByTeacherId(Long teacherId);

    Page<CircleResponse> getCirclesWithStudents(Pageable pageable);

    Long countStudentsByCircleId(Long circleId);

    List<CircleResponse> getCirclesWithoutTeacherByMosqueId(Long mosqueId);

    // Update operations
    CircleResponse updateCircle(Long id, CircleRequest request);

    void assignTeacherToCircle(Long circleId, Long teacherId);

    void removeTeacherFromCircle(Long circleId);

    // Delete operations
    void deleteCircle(Long id);

    // Course management in circles
    void addCourseToCircle(Long circleId, Long courseId);

    void removeCourseFromCircle(Long circleId, Long courseId);
}