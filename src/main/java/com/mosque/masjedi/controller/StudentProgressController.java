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

/**
 * Controller for managing student progress.
 */
@RestController
@RequestMapping("/api/student-progress")
@RequiredArgsConstructor
public class StudentProgressController {
    private final StudentProgressService studentProgressService;

    /**
     * Creates a new student progress entry.
     * 
     * @param request The StudentProgressRequest containing progress details
     * @return ResponseEntity containing created StudentProgressResponse with HTTP
     *         status 201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires ADMIN or TEACHER role
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<StudentProgressResponse> createStudentProgress(
            @Valid @RequestBody StudentProgressRequest request) {
        return new ResponseEntity<>(studentProgressService.createStudentProgress(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves a student progress entry by its ID.
     * 
     * @param id The ID of the student progress to retrieve
     * @return ResponseEntity containing StudentProgressResponse
     * @throws com.mosque.masjedi.exception.NotFoundException If student progress
     *                                                        not found
     * @apiNote Requires ADMIN/TEACHER role or student ownership of the progress
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @securityUtils.isStudentOwnerOfProgress(#id)")
    public ResponseEntity<StudentProgressResponse> getStudentProgressById(@PathVariable Long id) {
        return ResponseEntity.ok(studentProgressService.getStudentProgressById(id));
    }

    /**
     * Retrieves student progress entries by student and course.
     * 
     * @param studentId ID of the student to filter
     * @param courseId  ID of the course to filter
     * @return ResponseEntity containing List of StudentProgressResponses
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<List<StudentProgressResponse>> getStudentProgressByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentProgressService.getStudentProgressByStudentAndCourse(studentId, courseId));
    }

    /**
     * Retrieves completed lessons by student and course.
     * 
     * @param studentId ID of the student to filter
     * @param courseId  ID of the course to filter
     * @return ResponseEntity containing List of StudentProgressResponses
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}/course/{courseId}/completed")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<List<StudentProgressResponse>> getCompletedLessonsByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentProgressService.getCompletedLessonsByStudentAndCourse(studentId, courseId));
    }

    /**
     * Counts completed lessons by student and course.
     * 
     * @param studentId ID of the student to filter
     * @param courseId  ID of the course to filter
     * @return ResponseEntity containing the count of completed lessons
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}/course/{courseId}/completed/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<Long> countCompletedLessonsByStudentAndCourse(
            @PathVariable Long studentId, @PathVariable Long courseId) {
        return ResponseEntity.ok(studentProgressService.countCompletedLessonsByStudentAndCourse(studentId, courseId));
    }

    /**
     * Updates an existing student progress entry.
     * 
     * @param id      The ID of the student progress to update
     * @param request The StudentProgressRequest containing updated details
     * @return ResponseEntity containing updated StudentProgressResponse
     * @throws jakarta.validation.ValidationException         If request validation
     *                                                        fails
     * @throws com.mosque.masjedi.exception.NotFoundException If student progress
     *                                                        not found
     * @apiNote Requires ADMIN or TEACHER role
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<StudentProgressResponse> updateStudentProgress(
            @PathVariable Long id, @Valid @RequestBody StudentProgressRequest request) {
        return ResponseEntity.ok(studentProgressService.updateStudentProgress(id, request));
    }

    /**
     * Deletes a student progress entry by ID.
     * 
     * @param id The ID of the student progress to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires ADMIN or TEACHER role
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Void> deleteStudentProgress(@PathVariable Long id) {
        studentProgressService.deleteStudentProgress(id);
        return ResponseEntity.noContent().build();
    }
}