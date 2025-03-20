package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.LessonRequest;
import com.mosque.masjedi.dto.response.LessonResponse;
import com.mosque.masjedi.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller for managing lessons.
 */
@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    /**
     * Creates a new lesson.
     * 
     * @param request LessonRequest containing lesson details
     * @return ResponseEntity containing created LessonResponse with HTTP status 201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires ADMIN role
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LessonResponse> createLesson(@Valid @RequestBody LessonRequest request) {
        return new ResponseEntity<>(lessonService.createLesson(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves a lesson by its ID.
     * 
     * @param id ID of the lesson to retrieve
     * @return ResponseEntity containing LessonResponse
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If lesson not
     *                                                              found
     * @apiNote Requires authentication
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<LessonResponse> getLessonById(@PathVariable Long id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    /**
     * Retrieves all lessons associated with a course.
     * 
     * @param courseId ID of the course to filter
     * @return ResponseEntity containing List of LessonResponses
     * @apiNote Requires authentication
     */
    @GetMapping("/course/{courseId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LessonResponse>> getLessonsByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourseId(courseId));
    }

    /**
     * Retrieves lessons in a course ordered by their sequence number.
     * 
     * @param courseId ID of the course to filter
     * @return ResponseEntity containing ordered List of LessonResponses
     * @apiNote Requires authentication
     */
    @GetMapping("/course/{courseId}/ordered")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<LessonResponse>> getLessonsByCourseIdOrderByOrderAsc(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourseIdOrderByOrderAsc(courseId));
    }

    /**
     * Retrieves the highest order number for lessons in a course.
     * 
     * @param courseId ID of the course to check
     * @return ResponseEntity containing maximum order number
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/course/{courseId}/max-order")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Integer> getMaxOrderByCourseId(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getMaxOrderByCourseId(courseId));
    }

    /**
     * Checks if a lesson exists within a course.
     * 
     * @param courseId ID of the course to check
     * @param lessonId ID of the lesson to verify
     * @return ResponseEntity containing boolean existence check result
     * @apiNote Requires authentication
     */
    @GetMapping("/course/{courseId}/lesson/{lessonId}/exists")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Boolean> existsLessonInCourse(
            @PathVariable Long courseId, @PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.existsLessonInCourse(courseId, lessonId));
    }

    /**
     * Updates an existing lesson.
     * 
     * @param id      ID of the lesson to update
     * @param request LessonRequest containing updated details
     * @return ResponseEntity containing updated LessonResponse
     * @throws jakarta.validation.ValidationException               If request
     *                                                              validation fails
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If lesson not
     *                                                              found
     * @apiNote Requires ADMIN role
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LessonResponse> updateLesson(
            @PathVariable Long id, @Valid @RequestBody LessonRequest request) {
        return ResponseEntity.ok(lessonService.updateLesson(id, request));
    }

    /**
     * Deletes a lesson by ID.
     * 
     * @param id ID of the lesson to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires ADMIN role
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity.noContent().build();
    }
}