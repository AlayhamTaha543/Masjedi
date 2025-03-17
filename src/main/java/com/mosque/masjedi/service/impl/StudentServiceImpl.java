package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.response.CourseResponse;
import com.mosque.masjedi.dto.response.LogbookResponse;
import com.mosque.masjedi.dto.response.NoteResponse;
import com.mosque.masjedi.dto.response.StudentProgressResponse;
import com.mosque.masjedi.dto.response.UserResponse;
import com.mosque.masjedi.entity.enums.UserRole;
import com.mosque.masjedi.exception.UnauthorizedException;
import com.mosque.masjedi.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of StudentService that uses other services
 * and gets the current student from the security context
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {
    private final UserService userService;
    private final CourseService courseService;
    private final StudentProgressService progressService;
    private final LogbookService logbookService;
    private final NoteService noteService;

    @Override
    public List<CourseResponse> getStudentCourses() {
        Long studentId = getCurrentStudentId();
        return courseService.getCoursesByStudentId(studentId);
    }

    @Override
    public List<StudentProgressResponse> getStudentProgress(Long courseId) {
        Long studentId = getCurrentStudentId();
        return progressService.getStudentProgressByStudentAndCourse(studentId, courseId);
    }

    @Override
    public Long countCompletedLessons(Long courseId) {
        Long studentId = getCurrentStudentId();
        return progressService.countCompletedLessonsByStudentAndCourse(studentId, courseId);
    }

    @Override
    public List<LogbookResponse> getStudentDailyProgress(Long courseId, LocalDate date) {
        Long studentId = getCurrentStudentId();
        return logbookService.getLogbooksByStudentAndCourseAndDay(studentId, courseId, date);
    }

    @Override
    public Page<NoteResponse> getStudentNotes(Pageable pageable) {
        Long studentId = getCurrentStudentId();
        return noteService.getNotesByStudentId(studentId, pageable);
    }

    /**
     * Get the current student's ID from the security context
     * 
     * @return the student ID
     * @throws UnauthorizedException if the user is not authenticated or not a
     *                               student
     */
    private Long getCurrentStudentId() {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = jwt.getClaimAsString("preferred_username");

            UserResponse userResponse = userService.getUserByUsername(username);

            if (userResponse.role() != UserRole.STUDENT) {
                throw new UnauthorizedException("Access denied: User is not a student");
            }

            return userResponse.id();
        } catch (Exception e) {
            throw new UnauthorizedException("Authentication required");
        }
    }
}