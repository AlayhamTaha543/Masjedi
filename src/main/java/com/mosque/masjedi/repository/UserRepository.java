package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.User;
import com.mosque.masjedi.entity.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Basic queries
    Optional<User> findByUsername(String username);

    // Role-based queries
    Page<User> findByRole(UserRole role, Pageable pageable);

    // Circle-based queries
    @Query("SELECT u FROM User u WHERE u.circle.id = :circleId AND u.role = :role")
    List<User> findStudentsByCircleId(@Param("circleId") Long circleId, @Param("role") UserRole role);

    @Query("SELECT u FROM User u WHERE u.role = :role AND :circleId IN (SELECT c.id FROM u.taughtCircles c)")
    Optional<User> findTeacherByCircleId(@Param("circleId") Long circleId, @Param("role") UserRole role);

    // Mosque-based queries
    List<User> findByMosqueId(Long mosqueId);

    // Combined queries
    @Query("SELECT u FROM User u WHERE u.mosque.id = :mosqueId AND u.role = :role")
    Page<User> findByMosqueIdAndRole(@Param("mosqueId") Long mosqueId, @Param("role") UserRole role, Pageable pageable);

    // Transfer student to another circle
    @Modifying
    @Query("UPDATE User u SET u.circle.id = :newCircleId WHERE u.id = :studentId AND u.role = :role")
    void transferStudentToCircle(@Param("studentId") Long studentId, @Param("newCircleId") Long newCircleId,
            @Param("role") UserRole role);

    // Find teachers available for assignment
    @Query("SELECT u FROM User u WHERE u.role = :role AND (u.mosque.id = :mosqueId OR u.mosque IS NULL)")
    List<User> findAvailableTeachers(@Param("mosqueId") Long mosqueId, @Param("role") UserRole role);

    // Find students not assigned to any circle
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.circle IS NULL AND u.mosque.id = :mosqueId")
    List<User> findUnassignedStudentsByMosqueId(@Param("mosqueId") Long mosqueId, @Param("role") UserRole role);

    // Find students by circle and course progress
    @Query("SELECT DISTINCT u FROM User u JOIN StudentProgress sp ON sp.student.id = u.id JOIN Lesson l ON sp.lesson.id = l.id WHERE u.circle.id = :circleId AND l.course.id = :courseId AND u.role = :role")
    List<User> findStudentsByCircleIdAndCourseId(@Param("circleId") Long circleId, @Param("courseId") Long courseId,
            @Param("role") UserRole role);
}