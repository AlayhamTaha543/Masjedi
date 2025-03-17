package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.request.CircleRequest;
import com.mosque.masjedi.dto.response.CircleResponse;
import com.mosque.masjedi.entity.Circle;
import com.mosque.masjedi.exception.NotFoundException;
import com.mosque.masjedi.mapper.CircleMapper;
import com.mosque.masjedi.repository.CircleRepository;
import com.mosque.masjedi.service.CircleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CircleServiceImpl implements CircleService {
    private final CircleRepository circleRepository;
    private final CircleMapper circleMapper;

    @Override
    @Transactional
    public CircleResponse createCircle(CircleRequest request) {
        Circle circle = circleMapper.toEntity(request);
        circle = circleRepository.save(circle);
        return circleMapper.toDto(circle);
    }

    @Override
    public CircleResponse getCircleById(Long id) {
        return circleRepository.findById(id)
                .map(circleMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Circle not found"));
    }

    @Override
    public Page<CircleResponse> getCirclesByMosqueId(Long mosqueId, Pageable pageable) {
        return circleRepository.findByMosqueId(mosqueId, pageable)
                .map(circleMapper::toDto);
    }

    @Override
    public List<CircleResponse> getCirclesByTeacherId(Long teacherId) {
        return circleRepository.findByTeacherId(teacherId)
                .stream()
                .map(circleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CircleResponse> getCirclesWithStudents(Pageable pageable) {
        return circleRepository.findCirclesWithStudents(pageable)
                .map(circleMapper::toDto);
    }

    @Override
    public Long countStudentsByCircleId(Long circleId) {
        return circleRepository.countStudentsByCircleId(circleId);
    }

    @Override
    public List<CircleResponse> getCirclesWithoutTeacherByMosqueId(Long mosqueId) {
        return circleRepository.findCirclesWithoutTeacherByMosqueId(mosqueId)
                .stream()
                .map(circleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CircleResponse updateCircle(Long id, CircleRequest request) {
        Circle circle = circleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Circle not found"));
        circleMapper.updateEntity(request, circle);
        circle = circleRepository.save(circle);
        return circleMapper.toDto(circle);
    }

    @Override
    @Transactional
    public void assignTeacherToCircle(Long circleId, Long teacherId) {
        if (!circleRepository.existsById(circleId)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.assignTeacherToCircle(circleId, teacherId);
    }

    @Override
    @Transactional
    public void removeTeacherFromCircle(Long circleId) {
        if (!circleRepository.existsById(circleId)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.removeTeacherFromCircle(circleId);
    }

    @Override
    @Transactional
    public void deleteCircle(Long id) {
        if (!circleRepository.existsById(id)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void addCourseToCircle(Long circleId, Long courseId) {
        if (!circleRepository.existsById(circleId)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.addCourseToCircle(circleId, courseId);
    }

    @Override
    @Transactional
    public void removeCourseFromCircle(Long circleId, Long courseId) {
        if (!circleRepository.existsById(circleId)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.removeCourseFromCircle(circleId, courseId);
    }
}