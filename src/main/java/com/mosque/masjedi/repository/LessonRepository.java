package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.Lesson;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // Course-based queries
    List<Lesson> findByCourseId(Long courseId);

    // Order-based queries
    List<Lesson> findByCourseIdOrderByOrderAsc(Long courseId);

    // Find the maximum order in a course (for appending new lessons)
    @Query("SELECT MAX(l.order) FROM Lesson l WHERE l.course.id = :courseId")
    Integer findMaxOrderByCourseId(@Param("courseId") Long courseId);

    // Check if a lesson exists in a specific course
    boolean existsByCourseIdAndId(Long courseId, Long lessonId);

    @Modifying
    @Query("UPDATE Lesson l SET l.order = l.order + 1 WHERE l.course.id = :courseId AND l.order >= :newOrder")
    void shiftLessonsForInsert(@Param("courseId") Long courseId, @Param("newOrder") Integer newOrder);
}