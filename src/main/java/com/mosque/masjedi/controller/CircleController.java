package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.CircleRequest;
import com.mosque.masjedi.dto.response.CircleResponse;
import com.mosque.masjedi.service.CircleService;
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
@RequestMapping("/api/circles")
@RequiredArgsConstructor
public class CircleController {
    private final CircleService circleService;

    /**
     * Creates a new circle with the provided details.
     * 
     * @param request The CircleRequest containing circle details
     * @return ResponseEntity containing the created CircleResponse and HTTP status
     *         201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires ADMIN role
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CircleResponse> createCircle(@Valid @RequestBody CircleRequest request) {
        return new ResponseEntity<>(circleService.createCircle(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves a circle by its unique identifier.
     * 
     * @param id The ID of the circle to retrieve
     * @return ResponseEntity containing the CircleResponse
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If circle not
     *                                                              found
     * @apiNote Requires authentication
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CircleResponse> getCircleById(@PathVariable Long id) {
        return ResponseEntity.ok(circleService.getCircleById(id));
    }

    /**
     * Retrieves paginated list of circles associated with a specific mosque.
     * 
     * @param mosqueId The ID of the mosque to filter circles
     * @param pageable Pagination configuration (page number, size, sorting)
     * @return ResponseEntity containing Page of CircleResponses
     * @apiNote Requires authentication
     */
    @GetMapping("/mosque/{mosqueId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<CircleResponse>> getCirclesByMosqueId(
            @PathVariable Long mosqueId, Pageable pageable) {
        return ResponseEntity.ok(circleService.getCirclesByMosqueId(mosqueId, pageable));
    }

    /**
     * Retrieves all circles taught by a specific teacher.
     * 
     * @param teacherId The ID of the teacher to filter circles
     * @return ResponseEntity containing List of CircleResponses
     * @apiNote Requires ADMIN or TEACHER role
     */
    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<CircleResponse>> getCirclesByTeacherId(@PathVariable Long teacherId) {
        return ResponseEntity.ok(circleService.getCirclesByTeacherId(teacherId));
    }

    /**
     * Retrieves paginated list of circles with their associated students.
     * 
     * @param pageable Pagination configuration (page number, size, sorting)
     * @return ResponseEntity containing Page of CircleResponses with students
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/with-students")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<CircleResponse>> getCirclesWithStudents(Pageable pageable) {
        return ResponseEntity.ok(circleService.getCirclesWithStudents(pageable));
    }

    /**
     * Counts the number of students enrolled in a specific circle.
     * 
     * @param id The ID of the circle to count students for
     * @return ResponseEntity containing the student count
     * @apiNote Requires authentication
     */
    @GetMapping("/{id}/students/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> countStudentsByCircleId(@PathVariable Long id) {
        return ResponseEntity.ok(circleService.countStudentsByCircleId(id));
    }

    /**
     * Retrieves circles without assigned teachers in a specific mosque.
     * 
     * @param mosqueId The ID of the mosque to filter circles
     * @return ResponseEntity containing List of CircleResponses
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/without-teacher/mosque/{mosqueId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CircleResponse>> getCirclesWithoutTeacherByMosqueId(@PathVariable Long mosqueId) {
        return ResponseEntity.ok(circleService.getCirclesWithoutTeacherByMosqueId(mosqueId));
    }

    /**
     * Updates an existing circle with new details.
     * 
     * @param id      The ID of the circle to update
     * @param request The CircleRequest containing updated details
     * @return ResponseEntity containing the updated CircleResponse
     * @throws jakarta.validation.ValidationException               If request
     *                                                              validation fails
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If circle not
     *                                                              found
     * @apiNote Requires ADMIN role
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CircleResponse> updateCircle(
            @PathVariable Long id, @Valid @RequestBody CircleRequest request) {
        return ResponseEntity.ok(circleService.updateCircle(id, request));
    }

    /**
     * Assigns a teacher to a circle.
     * 
     * @param id        The ID of the circle to update
     * @param teacherId The ID of the teacher to assign
     * @return Empty ResponseEntity with HTTP status 200
     * @apiNote Requires ADMIN role
     */
    @PutMapping("/{id}/assign-teacher/{teacherId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> assignTeacherToCircle(
            @PathVariable Long id, @PathVariable Long teacherId) {
        circleService.assignTeacherToCircle(id, teacherId);
        return ResponseEntity.ok().build();
    }

    /**
     * Removes the assigned teacher from a circle.
     * 
     * @param id The ID of the circle to update
     * @return Empty ResponseEntity with HTTP status 200
     * @apiNote Requires ADMIN role
     */
    @PutMapping("/{id}/remove-teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeTeacherFromCircle(@PathVariable Long id) {
        circleService.removeTeacherFromCircle(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a circle by its ID.
     * 
     * @param id The ID of the circle to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires ADMIN role
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCircle(@PathVariable Long id) {
        circleService.deleteCircle(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Associates a course with a circle.
     * 
     * @param id       The ID of the circle to update
     * @param courseId The ID of the course to add
     * @return Empty ResponseEntity with HTTP status 200
     * @apiNote Requires ADMIN role
     */
    @PostMapping("/{id}/add-course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addCourseToCircle(
            @PathVariable Long id, @PathVariable Long courseId) {
        circleService.addCourseToCircle(id, courseId);
        return ResponseEntity.ok().build();
    }

    /**
     * Removes a course association from a circle.
     * 
     * @param id       The ID of the circle to update
     * @param courseId The ID of the course to remove
     * @return Empty ResponseEntity with HTTP status 200
     * @apiNote Requires ADMIN role
     */
    @DeleteMapping("/{id}/remove-course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeCourseFromCircle(
            @PathVariable Long id, @PathVariable Long courseId) {
        circleService.removeCourseFromCircle(id, courseId);
        return ResponseEntity.ok().build();
    }
}