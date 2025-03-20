package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.LogbookRequest;
import com.mosque.masjedi.dto.response.LogbookResponse;
import com.mosque.masjedi.service.LogbookService;
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
 * Controller for managing logbooks.
 */
@RestController
@RequestMapping("/api/logbook")
@RequiredArgsConstructor
public class LogbookController {
    private final LogbookService logbookService;

    /**
     * Creates a new logbook entry.
     * 
     * @param request The LogbookRequest containing logbook details
     * @return ResponseEntity containing created LogbookResponse with HTTP status
     *         201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires ADMIN or TEACHER role
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<LogbookResponse> createLogbook(
            @Valid @RequestBody LogbookRequest request) {
        return new ResponseEntity<>(logbookService.createLogbook(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves a specific logbook entry by its ID.
     * 
     * @param id The ID of the logbook to retrieve
     * @return ResponseEntity containing LogbookResponse
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If logbook not
     *                                                              found
     * @apiNote Requires ADMIN/TEACHER role or student ownership of the logbook
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @securityUtils.isStudentOwnerOfLogbook(#id)")
    public ResponseEntity<LogbookResponse> getLogbookById(@PathVariable Long id) {
        return ResponseEntity.ok(logbookService.getLogbookById(id));
    }

    /**
     * Retrieves logbooks for a student in a specific course on a given date.
     * 
     * @param studentId ID of the student to filter
     * @param courseId  ID of the course to filter
     * @param date      Date to filter logbooks (ISO format: yyyy-MM-dd)
     * @return ResponseEntity containing List of LogbookResponses
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}/course/{courseId}/date")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<List<LogbookResponse>> getLogbooksByStudentAndCourseAndDay(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(
                logbookService.getLogbooksByStudentAndCourseAndDay(studentId, courseId, date));
    }

    /**
     * Retrieves all logbooks for a student on a specific date.
     * 
     * @param studentId ID of the student to filter
     * @param date      Date to filter logbooks (ISO format: yyyy-MM-dd)
     * @return ResponseEntity containing List of LogbookResponses
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}/date")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<List<LogbookResponse>> getLogbooksByStudentAndDay(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(logbookService.getLogbooksByStudentAndDay(studentId, date));
    }

    /**
     * Retrieves logbooks for a student in a course within a date range.
     * 
     * @param studentId ID of the student to filter
     * @param courseId  ID of the course to filter
     * @param startDate Start date of range (inclusive)
     * @param endDate   End date of range (inclusive)
     * @return ResponseEntity containing List of LogbookResponses
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}/course/{courseId}/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<List<LogbookResponse>> getLogbooksByStudentAndCourseAndDateRange(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(logbookService.getLogbooksByStudentAndCourseAndDateRange(
                studentId, courseId, startDate, endDate));
    }

    /**
     * Retrieves paginated logbooks for a student in a specific course.
     * 
     * @param studentId ID of the student to filter
     * @param courseId  ID of the course to filter
     * @param pageable  Pagination configuration
     * @return ResponseEntity containing Page of LogbookResponses
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}/course/{courseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<Page<LogbookResponse>> getLogbooksByStudentAndCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId,
            Pageable pageable) {
        return ResponseEntity.ok(
                logbookService.getLogbooksByStudentAndCourse(studentId, courseId, pageable));
    }

    /**
     * Retrieves logbooks for a circle and course on a specific date.
     * 
     * @param circleId ID of the circle to filter
     * @param courseId ID of the course to filter
     * @param date     Date to filter logbooks (ISO format: yyyy-MM-dd)
     * @return ResponseEntity containing List of LogbookResponses
     * @apiNote Requires ADMIN or TEACHER role
     */
    @GetMapping("/circle/{circleId}/course/{courseId}/date")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<LogbookResponse>> getLogbooksByCircleAndCourseAndDay(
            @PathVariable Long circleId,
            @PathVariable Long courseId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(
                logbookService.getLogbooksByCircleAndCourseAndDay(circleId, courseId, date));
    }

    /**
     * Deletes a logbook entry by ID.
     * 
     * @param id ID of the logbook to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires ADMIN or TEACHER role
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<Void> deleteLogbook(@PathVariable Long id) {
        logbookService.deleteLogbook(id);
        return ResponseEntity.noContent().build();
    }
}
