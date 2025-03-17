package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.response.CourseResponse;
import com.mosque.masjedi.dto.response.LogbookResponse;
import com.mosque.masjedi.dto.response.NoteResponse;
import com.mosque.masjedi.dto.response.StudentProgressResponse;
import com.mosque.masjedi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for student-specific operations
 */
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/courses")
    public ResponseEntity<List<CourseResponse>> getStudentCourses() {
        return ResponseEntity.ok(studentService.getStudentCourses());
    }

    @GetMapping("/progress/{courseId}")
    public ResponseEntity<List<StudentProgressResponse>> getStudentProgress(@PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.getStudentProgress(courseId));
    }

    @GetMapping("/progress/{courseId}/completed/count")
    public ResponseEntity<Long> countCompletedLessons(@PathVariable Long courseId) {
        return ResponseEntity.ok(studentService.countCompletedLessons(courseId));
    }

    @GetMapping("/daily-progress/{courseId}")
    public ResponseEntity<List<LogbookResponse>> getStudentDailyProgress(
            @PathVariable Long courseId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // Default to today's date if not provided
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        return ResponseEntity.ok(studentService.getStudentDailyProgress(courseId, targetDate));
    }

    @GetMapping("/notes")
    public ResponseEntity<Page<NoteResponse>> getStudentNotes(Pageable pageable) {
        return ResponseEntity.ok(studentService.getStudentNotes(pageable));
    }
}