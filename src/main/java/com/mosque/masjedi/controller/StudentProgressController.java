package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.StudentProgressRequest;
import com.mosque.masjedi.dto.response.StudentProgressResponse;
import com.mosque.masjedi.service.StudentProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/student-progress")
@RequiredArgsConstructor
public class StudentProgressController {
    private final StudentProgressService studentProgressService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<StudentProgressResponse> createStudentProgress(
            @Valid @RequestBody StudentProgressRequest request) {
        return new ResponseEntity<>(studentProgressService.createStudentProgress(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @securityUtils.isStudentOwnerOfProgress(#id)")
    public ResponseEntity<StudentProgressResponse> getStudentProgressById(@PathVariable Long id) {
        return ResponseEntity.ok(studentProgressService.getStudentProgressById(id));
    }

    @GetMapping("/student/{studentId}/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<List<StudentProgressResponse>> getStudentProgressByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentProgressService.getStudentProgressByStudentAndCourse(studentId, courseId));
    }

    @GetMapping("/student/{studentId}/course/{courseId}/completed")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<List<StudentProgressResponse>> getCompletedLessonsByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentProgressService.getCompletedLessonsByStudentAndCourse(studentId, courseId));
    }

    @GetMapping("/student/{studentId}/course/{courseId}/completed/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<Long> countCompletedLessonsByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentProgressService.countCompletedLessonsByStudentAndCourse(studentId, courseId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<StudentProgressResponse> updateStudentProgress(
            @PathVariable Long id, @Valid @RequestBody StudentProgressRequest request) {
        return ResponseEntity.ok(studentProgressService.updateStudentProgress(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Void> deleteStudentProgress(@PathVariable Long id) {
        studentProgressService.deleteStudentProgress(id);
        return ResponseEntity.noContent().build();
    }
}