package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.StudentProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentProgressRepository extends JpaRepository<StudentProgress, Long> {
    // Student progress queries
    List<StudentProgress> findByStudentIdAndLessonCourseId(Long studentId, Long courseId);

    // Completion status queries
    @Query("SELECT sp FROM StudentProgress sp WHERE sp.student.id = :studentId AND sp.lesson.course.id = :courseId AND sp.isCompleted = true")
    List<StudentProgress> findCompletedLessonsByStudentAndCourse(@Param("studentId") Long studentId,
            @Param("courseId") Long courseId);

    // Statistics queries
    @Query("SELECT COUNT(sp) FROM StudentProgress sp WHERE sp.student.id = :studentId AND sp.lesson.course.id = :courseId AND sp.isCompleted = true")
    Long countCompletedLessons(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}