package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.LogbookRequest;
import com.mosque.masjedi.dto.response.LogbookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for logbook-related operations.
 */
public interface LogbookService {

    /**
     * Create a new logbook entry.
     *
     * @param request the logbook request
     * @return the created logbook response
     */
    LogbookResponse createLogbook(LogbookRequest request);

    /**
     * Get a logbook entry by ID.
     *
     * @param id the ID of the logbook entry
     * @return the logbook response
     */
    LogbookResponse getLogbookById(Long id);

    /**
     * Get logbook entries by student, course, and day.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @param date      the date of the logbook entries
     * @return the list of logbook responses
     */
    List<LogbookResponse> getLogbooksByStudentAndCourseAndDay(Long studentId, Long courseId, LocalDate date);

    /**
     * Get logbook entries by student and day.
     *
     * @param studentId the ID of the student
     * @param date      the date of the logbook entries
     * @return the list of logbook responses
     */
    List<LogbookResponse> getLogbooksByStudentAndDay(Long studentId, LocalDate date);

    /**
     * Get logbook entries by student, course, and date range.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @param startDate the start date of the logbook entries
     * @param endDate   the end date of the logbook entries
     * @return the list of logbook responses
     */
    List<LogbookResponse> getLogbooksByStudentAndCourseAndDateRange(Long studentId, Long courseId, LocalDate startDate,
            LocalDate endDate);

    /**
     * Get logbook entries by student and course with pagination.
     *
     * @param studentId the ID of the student
     * @param courseId  the ID of the course
     * @param pageable  the pagination information
     * @return the page of logbook responses
     */
    Page<LogbookResponse> getLogbooksByStudentAndCourse(Long studentId, Long courseId, Pageable pageable);

    /**
     * Get logbook entries by circle, course, and day.
     *
     * @param circleId the ID of the circle
     * @param courseId the ID of the course
     * @param date     the date of the logbook entries
     * @return the list of logbook responses
     */
    List<LogbookResponse> getLogbooksByCircleAndCourseAndDay(Long circleId, Long courseId, LocalDate date);

    /**
     * Update a logbook entry.
     *
     * @param id      the ID of the logbook entry
     * @param request the logbook request
     * @return the updated logbook response
     */
    LogbookResponse updateLogbook(Long id, LogbookRequest request);

    /**
     * Delete a logbook entry.
     *
     * @param id the ID of the logbook entry
     */
    void deleteLogbook(Long id);
}