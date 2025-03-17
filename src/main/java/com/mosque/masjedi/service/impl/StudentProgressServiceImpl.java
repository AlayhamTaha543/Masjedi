package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.request.StudentProgressRequest;
import com.mosque.masjedi.dto.response.StudentProgressResponse;
import com.mosque.masjedi.entity.Lesson;
import com.mosque.masjedi.entity.StudentProgress;
import com.mosque.masjedi.entity.User;
import com.mosque.masjedi.exception.NotFoundException;
import com.mosque.masjedi.mapper.StudentProgressMapper;
import com.mosque.masjedi.repository.LessonRepository;
import com.mosque.masjedi.repository.StudentProgressRepository;
import com.mosque.masjedi.repository.UserRepository;
import com.mosque.masjedi.service.StudentProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentProgressServiceImpl implements StudentProgressService {
    private final StudentProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final StudentProgressMapper progressMapper;

    @Override
    @Transactional
    public StudentProgressResponse createStudentProgress(StudentProgressRequest request) {
        User student = userRepository.findById(request.studentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Lesson lesson = lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        StudentProgress progress = progressMapper.toEntity(request);
        progress.setStudent(student);
        progress.setLesson(lesson);

        progress = progressRepository.save(progress);
        return progressMapper.toDto(progress);
    }

    @Override
    public StudentProgressResponse getStudentProgressById(Long id) {
        return progressRepository.findById(id)
                .map(progressMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Student progress not found"));
    }

    @Override
    public List<StudentProgressResponse> getStudentProgressByStudentAndCourse(Long studentId, Long courseId) {
        return progressRepository.findByStudentIdAndLessonCourseId(studentId, courseId)
                .stream()
                .map(progressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentProgressResponse> getCompletedLessonsByStudentAndCourse(Long studentId, Long courseId) {
        return progressRepository.findCompletedLessonsByStudentAndCourse(studentId, courseId)
                .stream()
                .map(progressMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Long countCompletedLessonsByStudentAndCourse(Long studentId, Long courseId) {
        return progressRepository.countCompletedLessons(studentId, courseId);
    }

    @Override
    @Transactional
    public StudentProgressResponse updateStudentProgress(Long id, StudentProgressRequest request) {
        StudentProgress progress = progressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student progress not found"));

        User student = userRepository.findById(request.studentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Lesson lesson = lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found"));

        progressMapper.updateEntity(request, progress);
        progress.setStudent(student);
        progress.setLesson(lesson);

        progress = progressRepository.save(progress);
        return progressMapper.toDto(progress);
    }

    @Override
    @Transactional
    public void deleteStudentProgress(Long id) {
        if (!progressRepository.existsById(id)) {
            throw new NotFoundException("Student progress not found");
        }
        progressRepository.deleteById(id);
    }
}