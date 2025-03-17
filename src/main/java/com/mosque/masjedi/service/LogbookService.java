package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.LogbookRequest;
import com.mosque.masjedi.dto.response.LogbookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface LogbookService {
    LogbookResponse createLogbook(LogbookRequest request);

    LogbookResponse getLogbookById(Long id);

    List<LogbookResponse> getLogbooksByStudentAndCourseAndDay(Long studentId, Long courseId, LocalDate date);

    List<LogbookResponse> getLogbooksByStudentAndDay(Long studentId, LocalDate date);

    List<LogbookResponse> getLogbooksByStudentAndCourseAndDateRange(Long studentId, Long courseId, LocalDate startDate,
            LocalDate endDate);

    Page<LogbookResponse> getLogbooksByStudentAndCourse(Long studentId, Long courseId, Pageable pageable);

    List<LogbookResponse> getLogbooksByCircleAndCourseAndDay(Long circleId, Long courseId, LocalDate date);

    LogbookResponse updateLogbook(Long id, LogbookRequest request);

    void deleteLogbook(Long id);
}