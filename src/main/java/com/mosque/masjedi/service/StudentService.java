package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.response.CourseResponse;
import com.mosque.masjedi.dto.response.LogbookResponse;
import com.mosque.masjedi.dto.response.NoteResponse;
import com.mosque.masjedi.dto.response.StudentProgressResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for student-specific operations
 */
public interface StudentService {
    // Course operations
    List<CourseResponse> getStudentCourses();

    // Progress operations
    List<StudentProgressResponse> getStudentProgress(Long courseId);

    Long countCompletedLessons(Long courseId);

    // Daily progress operations
    List<LogbookResponse> getStudentDailyProgress(Long courseId, LocalDate date);

    // Notes operations
    Page<NoteResponse> getStudentNotes(Pageable pageable);
}