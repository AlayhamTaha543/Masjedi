package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.request.CourseRequest;
import com.mosque.masjedi.dto.response.CourseResponse;
import com.mosque.masjedi.entity.Course;
import com.mosque.masjedi.exception.NotFoundException;
import com.mosque.masjedi.mapper.CourseMapper;
import com.mosque.masjedi.repository.CourseRepository;
import com.mosque.masjedi.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of CourseService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CourseResponse createCourse(CourseRequest request) {
        Course course = courseMapper.toEntity(request);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CourseResponse getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Course not found"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(courseMapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CourseResponse> getCoursesByCircleId(Long circleId) {
        return courseRepository.findByCircleId(circleId)
                .stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CourseResponse> getCoursesByStudentId(Long studentId) {
        return courseRepository.findByStudentId(studentId)
                .stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<CourseResponse> searchCoursesByTitle(String title, Pageable pageable) {
        return courseRepository.findByNameContainingIgnoreCase(title, pageable)
                .map(courseMapper::toDto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found"));
        courseMapper.updateEntity(request, course);
        course = courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new NotFoundException("Course not found");
        }
        courseRepository.deleteById(id);
    }
}