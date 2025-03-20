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

/**
 * Implementation of the CircleService interface.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CircleServiceImpl implements CircleService {
    private final CircleRepository circleRepository;
    private final CircleMapper circleMapper;

    /**
     * Creates a new circle.
     *
     * @param request the circle request
     * @return the created circle response
     */
    @Override
    @Transactional
    public CircleResponse createCircle(CircleRequest request) {
        Circle circle = circleMapper.toEntity(request);
        circle = circleRepository.save(circle);
        return circleMapper.toDto(circle);
    }

    /**
     * Gets a circle by its ID.
     *
     * @param id the circle ID
     * @return the circle response
     * @throws NotFoundException if the circle is not found
     */
    @Override
    public CircleResponse getCircleById(Long id) {
        return circleRepository.findById(id)
                .map(circleMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Circle not found"));
    }

    /**
     * Gets circles by mosque ID with pagination.
     *
     * @param mosqueId the mosque ID
     * @param pageable the pagination information
     * @return the paginated circle responses
     */
    @Override
    public Page<CircleResponse> getCirclesByMosqueId(Long mosqueId, Pageable pageable) {
        return circleRepository.findByMosqueId(mosqueId, pageable)
                .map(circleMapper::toDto);
    }

    /**
     * Gets circles by teacher ID.
     *
     * @param teacherId the teacher ID
     * @return the list of circle responses
     */
    @Override
    public List<CircleResponse> getCirclesByTeacherId(Long teacherId) {
        return circleRepository.findByTeacherId(teacherId)
                .stream()
                .map(circleMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets circles with students with pagination.
     *
     * @param pageable the pagination information
     * @return the paginated circle responses
     */
    @Override
    public Page<CircleResponse> getCirclesWithStudents(Pageable pageable) {
        return circleRepository.findCirclesWithStudents(pageable)
                .map(circleMapper::toDto);
    }

    /**
     * Counts the number of students in a circle.
     *
     * @param circleId the circle ID
     * @return the number of students
     */
    @Override
    public Long countStudentsByCircleId(Long circleId) {
        return circleRepository.countStudentsByCircleId(circleId);
    }

    /**
     * Gets circles without a teacher by mosque ID.
     *
     * @param mosqueId the mosque ID
     * @return the list of circle responses
     */
    @Override
    public List<CircleResponse> getCirclesWithoutTeacherByMosqueId(Long mosqueId) {
        return circleRepository.findCirclesWithoutTeacherByMosqueId(mosqueId)
                .stream()
                .map(circleMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates a circle.
     *
     * @param id      the circle ID
     * @param request the circle request
     * @return the updated circle response
     * @throws NotFoundException if the circle is not found
     */
    @Override
    @Transactional
    public CircleResponse updateCircle(Long id, CircleRequest request) {
        Circle circle = circleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Circle not found"));
        circleMapper.updateEntity(request, circle);
        circle = circleRepository.save(circle);
        return circleMapper.toDto(circle);
    }

    /**
     * Assigns a teacher to a circle.
     *
     * @param circleId  the circle ID
     * @param teacherId the teacher ID
     * @throws NotFoundException if the circle is not found
     */
    @Override
    @Transactional
    public void assignTeacherToCircle(Long circleId, Long teacherId) {
        if (!circleRepository.existsById(circleId)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.assignTeacherToCircle(circleId, teacherId);
    }

    /**
     * Removes a teacher from a circle.
     *
     * @param circleId the circle ID
     * @throws NotFoundException if the circle is not found
     */
    @Override
    @Transactional
    public void removeTeacherFromCircle(Long circleId) {
        if (!circleRepository.existsById(circleId)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.removeTeacherFromCircle(circleId);
    }

    /**
     * Deletes a circle.
     *
     * @param id the circle ID
     * @throws NotFoundException if the circle is not found
     */
    @Override
    @Transactional
    public void deleteCircle(Long id) {
        if (!circleRepository.existsById(id)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.deleteById(id);
    }

    /**
     * Adds a course to a circle.
     *
     * @param circleId the circle ID
     * @param courseId the course ID
     * @throws NotFoundException if the circle is not found
     */
    @Override
    @Transactional
    public void addCourseToCircle(Long circleId, Long courseId) {
        if (!circleRepository.existsById(circleId)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.addCourseToCircle(circleId, courseId);
    }

    /**
     * Removes a course from a circle.
     *
     * @param circleId the circle ID
     * @param courseId the course ID
     * @throws NotFoundException if the circle is not found
     */
    @Override
    @Transactional
    public void removeCourseFromCircle(Long circleId, Long courseId) {
        if (!circleRepository.existsById(circleId)) {
            throw new NotFoundException("Circle not found");
        }
        circleRepository.removeCourseFromCircle(circleId, courseId);
    }
}