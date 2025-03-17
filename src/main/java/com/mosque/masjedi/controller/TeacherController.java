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
 * Controller for teacher-specific operations
 */
@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@PreAuthorize("hasRole('TEACHER')")
public class TeacherController {
    private final TeacherService teacherService;

    // Circle management
    @GetMapping("/circles")
    public ResponseEntity<List<CircleResponse>> getTeacherCircles() {
        return ResponseEntity.ok(teacherService.getTeacherCircles());
    }

    @GetMapping("/circles/{circleId}")
    public ResponseEntity<CircleResponse> getCircleDetails(@PathVariable Long circleId) {
        return ResponseEntity.ok(teacherService.getCircleDetails(circleId));
    }

    @GetMapping("/circles/{circleId}/students")
    public ResponseEntity<List<UserResponse>> getCircleStudents(@PathVariable Long circleId) {
        return ResponseEntity.ok(teacherService.getCircleStudents(circleId));
    }

    // Student operations
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<CourseResponse>> getStudentCourses(@PathVariable Long studentId) {
        return ResponseEntity.ok(teacherService.getStudentCourses(studentId));
    }

    // Progress management
    @GetMapping("/students/{studentId}/courses/{courseId}/progress")
    public ResponseEntity<List<StudentProgressResponse>> getStudentProgress(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(teacherService.getStudentProgress(studentId, courseId));
    }

    @PostMapping("/progress")
    public ResponseEntity<StudentProgressResponse> updateStudentProgress(
            @Valid @RequestBody StudentProgressRequest request) {
        return new ResponseEntity<>(teacherService.updateStudentProgress(request), HttpStatus.CREATED);
    }

    // Daily progress management
    @GetMapping("/students/{studentId}/courses/{courseId}/daily-progress")
    public ResponseEntity<List<LogbookResponse>> getStudentDailyProgress(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(teacherService.getStudentDailyProgress(studentId, courseId, date));
    }

    @PostMapping("/daily-progress")
    public ResponseEntity<LogbookResponse> addDailyProgress(
            @Valid @RequestBody LogbookRequest request) {
        return new ResponseEntity<>(teacherService.addDailyProgress(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/daily-progress/{progressId}")
    public ResponseEntity<Void> deleteDailyProgress(@PathVariable Long progressId) {
        teacherService.deleteDailyProgress(progressId);
        return ResponseEntity.noContent().build();
    }

    // Notes management
    @GetMapping("/students/{studentId}/notes")
    public ResponseEntity<Page<NoteResponse>> getStudentNotes(
            @PathVariable Long studentId, Pageable pageable) {
        return ResponseEntity.ok(teacherService.getStudentNotes(studentId, pageable));
    }

    @PostMapping("/notes")
    public ResponseEntity<NoteResponse> addNote(@Valid @RequestBody NoteRequest request) {
        return new ResponseEntity<>(teacherService.addNote(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/notes/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable Long noteId) {
        teacherService.deleteNote(noteId);
        return ResponseEntity.noContent().build();
    }
}