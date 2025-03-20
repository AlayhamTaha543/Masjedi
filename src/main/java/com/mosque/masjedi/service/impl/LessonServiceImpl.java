package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.request.LessonRequest;
import com.mosque.masjedi.dto.response.LessonResponse;
import com.mosque.masjedi.entity.Lesson;
import com.mosque.masjedi.entity.Course;
import com.mosque.masjedi.exception.NotFoundException;
import com.mosque.masjedi.mapper.LessonMapper;
import com.mosque.masjedi.repository.CourseRepository;
import com.mosque.masjedi.repository.LessonRepository;
import com.mosque.masjedi.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of LessonService.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LessonResponse createLesson(LessonRequest request) {
        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        Lesson lesson = lessonMapper.toEntity(request);
        lesson.setCourse(course);
        lesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LessonResponse getLessonById(Long id) {
        return lessonRepository.findById(id)
                .map(lessonMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LessonResponse> getLessonsByCourseId(Long courseId) {
        return lessonRepository.findByCourseId(courseId)
                .stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LessonResponse> getLessonsByCourseIdOrderByOrderAsc(Long courseId) {
        return lessonRepository.findByCourseIdOrderByOrderAsc(courseId)
                .stream()
                .map(lessonMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMaxOrderByCourseId(Long courseId) {
        return lessonRepository.findMaxOrderByCourseId(courseId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsLessonInCourse(Long courseId, Long lessonId) {
        return lessonRepository.existsByCourseIdAndId(courseId, lessonId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public LessonResponse updateLesson(Long id, LessonRequest request) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new NotFoundException("Course not found"));

        lessonMapper.updateEntity(request, lesson);
        lesson.setCourse(course);
        lesson = lessonRepository.save(lesson);
        return lessonMapper.toDto(lesson);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteLesson(Long id) {
        if (!lessonRepository.existsById(id)) {
            throw new NotFoundException("Lesson not found");
        }
        lessonRepository.deleteById(id);
    }
}