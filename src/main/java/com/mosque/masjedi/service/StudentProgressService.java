package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.StudentProgressRequest;
import com.mosque.masjedi.dto.response.StudentProgressResponse;

import java.util.List;

public interface StudentProgressService {
    // Create operations
    StudentProgressResponse createStudentProgress(StudentProgressRequest request);

    // Read operations
    StudentProgressResponse getStudentProgressById(Long id);

    List<StudentProgressResponse> getStudentProgressByStudentAndCourse(Long studentId, Long courseId);

    List<StudentProgressResponse> getCompletedLessonsByStudentAndCourse(Long studentId, Long courseId);

    Long countCompletedLessonsByStudentAndCourse(Long studentId, Long courseId);

    // Update operations
    StudentProgressResponse updateStudentProgress(Long id, StudentProgressRequest request);

    // Delete operations
    void deleteStudentProgress(Long id);
}