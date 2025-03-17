package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
        // Get courses for a student through their progress
        @Query("SELECT DISTINCT c FROM Course c JOIN c.lessons l JOIN StudentProgress sp ON sp.lesson.id = l.id WHERE sp.student.id = :studentId")
        List<Course> findByStudentId(@Param("studentId") Long studentId);

        // Get courses for a circle through student progress
        @Query("SELECT DISTINCT c FROM Course c JOIN c.lessons l JOIN StudentProgress sp ON sp.lesson.id = l.id JOIN User s ON sp.student.id = s.id WHERE s.circle.id = :circleId")
        List<Course> findByCircleId(@Param("circleId") Long circleId);

        // Search courses by name
        Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);

        // Get courses with their lessons count
        @Query("SELECT c, COUNT(l) FROM Course c LEFT JOIN c.lessons l GROUP BY c")
        List<Object[]> findCoursesWithLessonCount();
}