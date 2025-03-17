package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.Logbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

import java.util.List;

public interface LogbookRepository extends JpaRepository<Logbook, Long> {
        // Daily progress by student, course and date
        @Query("SELECT l FROM Logbook l WHERE l.student.id = :studentId AND l.course.id = :courseId AND l.day = :date")
        List<Logbook> findByStudentIdAndCourseIdAndDay(
                        @Param("studentId") Long studentId,
                        @Param("courseId") Long courseId,
                        @Param("date") LocalDate date);

        // Daily progress for a student across all courses
        @Query("SELECT l FROM Logbook l WHERE l.student.id = :studentId AND l.day = :date")
        List<Logbook> findByStudentIdAndDay(
                        @Param("studentId") Long studentId,
                        @Param("date") LocalDate date);

        // Student progress within date range
        @Query("SELECT l FROM Logbook l WHERE l.student.id = :studentId AND l.course.id = :courseId AND l.day BETWEEN :startDate AND :endDate")
        List<Logbook> findByStudentIdAndCourseIdAndDateRange(
                        @Param("studentId") Long studentId,
                        @Param("courseId") Long courseId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);

        // Paginated student progress for admin/teacher view
        Page<Logbook> findByStudentIdAndCourseId(
                        Long studentId,
                        Long courseId,
                        Pageable pageable);

        // Daily progress for all students in a circle
        @Query("SELECT l FROM Logbook l WHERE l.student.circle.id = :circleId AND l.course.id = :courseId AND l.day = :date")
        List<Logbook> findByCircleIdAndCourseIdAndDay(
                        @Param("circleId") Long circleId,
                        @Param("courseId") Long courseId,
                        @Param("date") LocalDate date);
}