package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.LogbookRequest;
import com.mosque.masjedi.dto.request.NoteRequest;
import com.mosque.masjedi.dto.request.StudentProgressRequest;
import com.mosque.masjedi.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for teacher-specific operations
 */
public interface TeacherService {
    // Circle management
    List<CircleResponse> getTeacherCircles();

    CircleResponse getCircleDetails(Long circleId);

    List<UserResponse> getCircleStudents(Long circleId);

    // Student operations
    List<CourseResponse> getStudentCourses(Long studentId);

    // Progress management
    List<StudentProgressResponse> getStudentProgress(Long studentId, Long courseId);

    StudentProgressResponse updateStudentProgress(StudentProgressRequest request);

    // Daily progress management
    List<LogbookResponse> getStudentDailyProgress(Long studentId, Long courseId, LocalDate date);

    LogbookResponse addDailyProgress(LogbookRequest request);

    void deleteDailyProgress(Long progressId);

    // Notes management
    Page<NoteResponse> getStudentNotes(Long studentId, Pageable pageable);

    NoteResponse addNote(NoteRequest request);

    void deleteNote(Long noteId);
}