package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.LogbookRequest;
import com.mosque.masjedi.dto.request.NoteRequest;
import com.mosque.masjedi.dto.request.StudentProgressRequest;
import com.mosque.masjedi.dto.response.*;
import com.mosque.masjedi.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Controller for teacher-specific operations.
 */
@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {
    private final TeacherService teacherService;

    /**
     * Retrieves the circles managed by the teacher.
     * 
     * @return ResponseEntity containing List of CircleResponses
     * @apiNote Requires TEACHER role
     */
    @GetMapping("/circles")
    public ResponseEntity<List<CircleResponse>> getTeacherCircles() {
        return ResponseEntity.ok(teacherService.getTeacherCircles());
    }

    /**
     * Retrieves details of a specific circle.
     * 
     * @param circleId The ID of the circle to retrieve
     * @return ResponseEntity containing CircleResponse
     * @apiNote Requires TEACHER role
     */
    @GetMapping("/circles/{circleId}")
    public ResponseEntity<CircleResponse> getCircleDetails(@PathVariable Long circleId) {
        return ResponseEntity.ok(teacherService.getCircleDetails(circleId));
    }

    /**
     * Retrieves students in a specific circle.
     * 
     * @param circleId The ID of the circle to filter students
     * @return ResponseEntity containing List of UserResponses
     * @apiNote Requires TEACHER role
     */
    @GetMapping("/circles/{circleId}/students")
    public ResponseEntity<List<UserResponse>> getCircleStudents(@PathVariable Long circleId) {
        return ResponseEntity.ok(teacherService.getCircleStudents(circleId));
    }

    /**
     * Retrieves courses of a specific student.
     * 
     * @param studentId The ID of the student to filter courses
     * @return ResponseEntity containing List of CourseResponses
     * @apiNote Requires TEACHER role
     */
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<CourseResponse>> getStudentCourses(@PathVariable Long studentId) {
        return ResponseEntity.ok(teacherService.getStudentCourses(studentId));
    }

    /**
     * Retrieves progress of a student in a specific course.
     * 
     * @param studentId The ID of the student to filter progress
     * @param courseId  The ID of the course to filter progress
     * @return ResponseEntity containing List of StudentProgressResponses
     * @apiNote Requires TEACHER role
     */
    @GetMapping("/students/{studentId}/courses/{courseId}/progress")
    public ResponseEntity<List<StudentProgressResponse>> getStudentProgress(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(teacherService.getStudentProgress(studentId, courseId));
    }

    /**
     * Updates student progress.
     * 
     * @param request The StudentProgressRequest containing progress details
     * @return ResponseEntity containing created StudentProgressResponse with HTTP
     *         status 201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires TEACHER role
     */
    @PostMapping("/progress")
    public ResponseEntity<StudentProgressResponse> updateStudentProgress(
            @Valid @RequestBody StudentProgressRequest request) {
        return new ResponseEntity<>(teacherService.updateStudentProgress(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves daily progress of a student in a specific course.
     * 
     * @param studentId The ID of the student to filter progress
     * @param courseId  The ID of the course to filter progress
     * @param date      The date to filter progress (ISO format: yyyy-MM-dd)
     * @return ResponseEntity containing List of LogbookResponses
     * @apiNote Requires TEACHER role
     */
    @GetMapping("/students/{studentId}/courses/{courseId}/daily-progress")
    public ResponseEntity<List<LogbookResponse>> getStudentDailyProgress(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(teacherService.getStudentDailyProgress(studentId, courseId, date));
    }

    /**
     * Adds daily progress for a student.
     * 
     * @param request The LogbookRequest containing progress details
     * @return ResponseEntity containing created LogbookResponse with HTTP status
     *         201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires TEACHER role
     */
    @PostMapping("/daily-progress")
    public ResponseEntity<LogbookResponse> addDailyProgress(
            @Valid @RequestBody LogbookRequest request) {
        return new ResponseEntity<>(teacherService.addDailyProgress(request), HttpStatus.CREATED);
    }

    /**
     * Deletes a daily progress entry by ID.
     * 
     * @param progressId The ID of the daily progress to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires TEACHER role
     */
    @DeleteMapping("/daily-progress/{progressId}")
    public ResponseEntity<Void> deleteDailyProgress(@PathVariable Long progressId) {
        teacherService.deleteDailyProgress(progressId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves notes of a student with pagination.
     *
     * @param studentId The ID of the student to filter notes
     * @param pageable  The pagination information
     * @return ResponseEntity containing Page of NoteResponses
     * @apiNote Requires TEACHER role
     */
    @GetMapping("/students/{studentId}/notes")
    public ResponseEntity<Page<NoteResponse>> getStudentNotes(
            @PathVariable Long studentId, Pageable pageable) {
        return ResponseEntity.ok(teacherService.getStudentNotes(studentId, pageable));
    }

    /**
     * Adds a note for a student.
     * 
     * @param request The NoteRequest containing note details
     * @return ResponseEntity containing created NoteResponse with HTTP status 201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires TEACHER role
     */
    @PostMapping("/notes")
    public ResponseEntity<NoteResponse> addNote(@Valid @RequestBody NoteRequest request) {
        return new ResponseEntity<>(teacherService.addNote(request), HttpStatus.CREATED);
    }

    /**
     * Deletes a note by ID.
     * 
     * @param noteId The ID of the note to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires TEACHER role
     */
    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        teacherService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}