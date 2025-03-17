package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.CourseRequest;
import com.mosque.masjedi.dto.response.CourseResponse;
import com.mosque.masjedi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    /**
     * Creates a new course.
     * 
     * @param request CourseRequest containing course details
     * @return ResponseEntity containing created CourseResponse with HTTP status 201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires ADMIN role
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseRequest request) {
        return new ResponseEntity<>(courseService.createCourse(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves a course by its ID.
     * 
     * @param id ID of the course to retrieve
     * @return ResponseEntity containing CourseResponse
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If course not
     *                                                              found
     * @apiNote Requires authentication
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    /**
     * Retrieves paginated list of all courses.
     * 
     * @param pageable Pagination configuration
     * @return ResponseEntity containing Page of CourseResponses
     * @apiNote Requires authentication
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CourseResponse>> getAllCourses(Pageable pageable) {
        return ResponseEntity.ok(courseService.getAllCourses(pageable));
    }

    /**
     * Retrieves courses associated with a circle.
     * 
     * @param circleId ID of the circle to filter
     * @return ResponseEntity containing List of CourseResponses
     * @apiNote Requires authentication
     */
    @GetMapping("/circle/{circleId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<CourseResponse>> getCoursesByCircleId(@PathVariable Long circleId) {
        return ResponseEntity.ok(courseService.getCoursesByCircleId(circleId));
    }

    /**
     * Retrieves courses associated with a student.
     * 
     * @param studentId ID of the student to filter
     * @return ResponseEntity containing List of CourseResponses
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<List<CourseResponse>> getCoursesByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(courseService.getCoursesByStudentId(studentId));
    }

    /**
     * Searches courses by title with pagination.
     * 
     * @param title    Search term for course title
     * @param pageable Pagination configuration
     * @return ResponseEntity containing Page of CourseResponses
     * @apiNote Requires authentication
     */
    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CourseResponse>> searchCoursesByTitle(
            @RequestParam String title, Pageable pageable) {
        return ResponseEntity.ok(courseService.searchCoursesByTitle(title, pageable));
    }

    /**
     * Updates an existing course.
     * 
     * @param id      ID of the course to update
     * @param request CourseRequest containing updated details
     * @return ResponseEntity containing updated CourseResponse
     * @throws jakarta.validation.ValidationException               If request
     *                                                              validation fails
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If course not
     *                                                              found
     * @apiNote Requires ADMIN role
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    /**
     * Deletes a course by ID.
     * 
     * @param id ID of the course to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires ADMIN role
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}