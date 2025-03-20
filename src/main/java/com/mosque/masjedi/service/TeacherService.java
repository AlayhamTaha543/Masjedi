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
 * Service interface for teacher-specific operations.
 */
public interface TeacherService {
    // Circle management

    /**
     * Get the circles managed by the teacher.
     *
     * @return the list of circle responses
     */
    List<CircleResponse> getTeacherCircles();

    /**
     * Get the details of a circle.
     *
     * @param circleId the ID of the circle
     * @return the circle response
     */
    CircleResponse getCircleDetails(Long circleId);

    /**
     * Get the students enrolled in a circle.
     *
     * @param circleId the ID of the circle
     * @return the list of student responses
     */
    List<UserResponse> getCircleStudents(Long circleId);

    // Student operations

    /**
     * Get the courses a student is enrolled in.
     *
     * @param studentId the ID of the student
     * @return the list of course responses
     */
    List<CourseResponse> getStudentCourses(Long studentId);

    // Progress management

    /**
     * Get the progress of a student in a course.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @return the list of student progress responses
     */
    List<StudentProgressResponse> getStudentProgress(Long studentId, Long courseId);

    /**
     * Update the progress of a student.
     *
     * @param request the student progress request
     * @return the updated student progress response
     */
    StudentProgressResponse updateStudentProgress(StudentProgressRequest request);

    // Daily progress management

    /**
     * Get the daily progress of a student in a course.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @param date      the date of the progress
     * @return the list of logbook responses
     */
    List<LogbookResponse> getStudentDailyProgress(Long studentId, Long courseId, LocalDate date);

    /**
     * Add a daily progress record for a student.
     *
     * @param request the logbook request
     * @return the created logbook response
     */
    LogbookResponse addDailyProgress(LogbookRequest request);

    /**
     * Delete a daily progress record.
     *
     * @param progressId the ID of the progress record
     */
    void deleteDailyProgress(Long progressId);

    // Notes management

    /**
     * Get the notes of a student.
     *
     * @param studentId the ID of the student
     * @param pageable  the pagination information
     * @return the paginated list of note responses
     */
    Page<NoteResponse> getStudentNotes(Long studentId, Pageable pageable);

    /**
     * Add a note for a student.
     *
     * @param request the note request
     * @return the created note response
     */
    NoteResponse addNote(NoteRequest request);

    /**
     * Delete a note.
     *
     * @param noteId the ID of the note
     */
    void deleteNote(Long noteId);
}