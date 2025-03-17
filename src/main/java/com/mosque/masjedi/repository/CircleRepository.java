package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.Circle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CircleRepository extends JpaRepository<Circle, Long> {
    // Mosque-based queries
    Page<Circle> findByMosqueId(Long mosqueId, Pageable pageable);

    // Teacher-based queries
    List<Circle> findByTeacherId(Long teacherId);

    // Statistics queries
    @Query("SELECT c FROM Circle c LEFT JOIN c.students s GROUP BY c HAVING COUNT(s) > 0")
    Page<Circle> findCirclesWithStudents(Pageable pageable);

    @Query("SELECT COUNT(s) FROM Circle c JOIN c.students s WHERE c.id = :circleId")
    Long countStudentsByCircleId(@Param("circleId") Long circleId);

    // Teacher assignment
    @Modifying
    @Query("UPDATE Circle c SET c.teacher.id = :teacherId WHERE c.id = :circleId")
    void assignTeacherToCircle(@Param("circleId") Long circleId, @Param("teacherId") Long teacherId);

    @Modifying
    @Query("UPDATE Circle c SET c.teacher = NULL WHERE c.id = :circleId")
    void removeTeacherFromCircle(@Param("circleId") Long circleId);

    // Find circles without teachers
    @Query("SELECT c FROM Circle c WHERE c.teacher IS NULL AND c.mosque.id = :mosqueId")
    List<Circle> findCirclesWithoutTeacherByMosqueId(@Param("mosqueId") Long mosqueId);

    // Course management in circles
    @Modifying
    @Query(value = "INSERT INTO circle_courses (circle_id, course_id) VALUES (:circleId, :courseId)", nativeQuery = true)
    void addCourseToCircle(@Param("circleId") Long circleId, @Param("courseId") Long courseId);

    @Modifying
    @Query(value = "DELETE FROM circle_courses WHERE circle_id = :circleId AND course_id = :courseId", nativeQuery = true)
    void removeCourseFromCircle(@Param("circleId") Long circleId, @Param("courseId") Long courseId);
}