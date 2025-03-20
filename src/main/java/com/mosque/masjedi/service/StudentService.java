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
 * Service interface for student-specific operations.
 */
public interface StudentService {
    // Course operations

    /**
     * Get the courses the student is enrolled in.
     *
     * @return the list of course responses
     */
    List<CourseResponse> getStudentCourses();

    // Progress operations

    /**
     * Get the progress of the student in a course.
     *
     * @param courseId the ID of the course
     * @return the list of student progress responses
     */
    List<StudentProgressResponse> getStudentProgress(Long courseId);

    /**
     * Count the completed lessons in a course.
     *
     * @param courseId the ID of the course
     * @return the number of completed lessons
     */
    Long countCompletedLessons(Long courseId);

    // Daily progress operations

    /**
     * Get the daily progress of the student in a course.
     *
     * @param courseId the ID of the course
     * @param date     the date of the progress
     * @return the list of logbook responses
     */
    List<LogbookResponse> getStudentDailyProgress(Long courseId, LocalDate date);

    // Notes operations

    /**
     * Get the notes of the student.
     *
     * @param pageable the pagination information
     * @return the paginated list of note responses
     */
    Page<NoteResponse> getStudentNotes(Pageable pageable);
}