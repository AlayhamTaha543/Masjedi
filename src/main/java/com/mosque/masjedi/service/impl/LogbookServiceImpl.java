package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.request.LogbookRequest;
import com.mosque.masjedi.dto.response.LogbookResponse;
import com.mosque.masjedi.entity.Course;
import com.mosque.masjedi.entity.Logbook;
import com.mosque.masjedi.entity.User;
import com.mosque.masjedi.exception.NotFoundException;
import com.mosque.masjedi.mapper.LogbookMapper;
import com.mosque.masjedi.repository.CourseRepository;
import com.mosque.masjedi.repository.LogbookRepository;
import com.mosque.masjedi.repository.UserRepository;
import com.mosque.masjedi.service.LogbookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of LogbookService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LogbookServiceImpl implements LogbookService {
        private final LogbookRepository logbookRepository;
        private final UserRepository userRepository;
        private final CourseRepository courseRepository;
        private final LogbookMapper logbookMapper;

        /**
         * {@inheritDoc}
         */
        @Override
        @Transactional
        public LogbookResponse createLogbook(LogbookRequest request) {
                User student = userRepository.findById(request.studentId())
                                .orElseThrow(() -> new NotFoundException("Student not found"));

                Course course = courseRepository.findById(request.courseId())
                                .orElseThrow(() -> new NotFoundException("Course not found"));

                Logbook log = logbookMapper.toEntity(request);
                log.setStudent(student);
                log.setCourse(course);

                log = logbookRepository.save(log);
                return logbookMapper.toDto(log);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public LogbookResponse getLogbookById(Long id) {
                return logbookRepository.findById(id)
                                .map(logbookMapper::toDto)
                                .orElseThrow(() -> new NotFoundException("Logbook not found"));
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<LogbookResponse> getLogbooksByStudentAndCourseAndDay(Long studentId, Long courseId,
                        LocalDate date) {
                return logbookRepository.findByStudentIdAndCourseIdAndDay(studentId, courseId, date)
                                .stream()
                                .map(logbookMapper::toDto)
                                .collect(Collectors.toList());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<LogbookResponse> getLogbooksByStudentAndDay(Long studentId, LocalDate date) {
                return logbookRepository.findByStudentIdAndDay(studentId, date)
                                .stream()
                                .map(logbookMapper::toDto)
                                .collect(Collectors.toList());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<LogbookResponse> getLogbooksByStudentAndCourseAndDateRange(Long studentId, Long courseId,
                        LocalDate startDate, LocalDate endDate) {
                return logbookRepository.findByStudentIdAndCourseIdAndDateRange(studentId, courseId, startDate, endDate)
                                .stream()
                                .map(logbookMapper::toDto)
                                .collect(Collectors.toList());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Page<LogbookResponse> getLogbooksByStudentAndCourse(Long studentId, Long courseId, Pageable pageable) {
                return logbookRepository.findByStudentIdAndCourseId(studentId, courseId, pageable)
                                .map(logbookMapper::toDto);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<LogbookResponse> getLogbooksByCircleAndCourseAndDay(Long circleId, Long courseId, LocalDate date) {
                return logbookRepository.findByCircleIdAndCourseIdAndDay(circleId, courseId, date)
                                .stream()
                                .map(logbookMapper::toDto)
                                .collect(Collectors.toList());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Transactional
        public LogbookResponse updateLogbook(Long id, LogbookRequest request) {
                Logbook log = logbookRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("Logbook not found"));

                User student = userRepository.findById(request.studentId())
                                .orElseThrow(() -> new NotFoundException("Student not found"));

                Course course = courseRepository.findById(request.courseId())
                                .orElseThrow(() -> new NotFoundException("Course not found"));

                logbookMapper.updateEntity(request, log);
                log.setStudent(student);
                log.setCourse(course);

                log = logbookRepository.save(log);
                return logbookMapper.toDto(log);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Transactional
        public void deleteLogbook(Long id) {
                if (!logbookRepository.existsById(id)) {
                        throw new NotFoundException("Logbook not found");
                }
                logbookRepository.deleteById(id);
        }
}