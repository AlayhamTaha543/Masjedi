package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.Mosque;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MosqueRepository extends JpaRepository<Mosque, Long> {
    // Basic queries
    List<Mosque> findByTitleContainingIgnoreCase(String title);

    Page<Mosque> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    // Combined queries
    @Query("SELECT m FROM Mosque m WHERE m.title LIKE %:keyword% OR m.description LIKE %:keyword% OR m.location LIKE %:keyword%")
    Page<Mosque> searchMosques(@Param("keyword") String keyword, Pageable pageable);

    // Statistics queries
    @Query("SELECT m, COUNT(c) FROM Mosque m LEFT JOIN m.circles c GROUP BY m")
    List<Object[]> findMosquesWithCircleCount();

    @Query("SELECT m, COUNT(u) FROM Mosque m LEFT JOIN User u ON u.mosque.id = m.id WHERE u.role = 'STUDENT' GROUP BY m")
    List<Object[]> findMosquesWithStudentCount();

    @Query("SELECT m, COUNT(u) FROM Mosque m LEFT JOIN User u ON u.mosque.id = m.id WHERE u.role = 'TEACHER' GROUP BY m")
    List<Object[]> findMosquesWithTeacherCount();

    // Active mosques (with active circles)
    @Query("SELECT DISTINCT m FROM Mosque m JOIN m.circles c JOIN c.students u JOIN StudentProgress sp ON sp.student.id = u.id")
    List<Mosque> findActiveMosques();

    // Most active mosques
    @Query("SELECT m, COUNT(DISTINCT sp) FROM Mosque m JOIN m.circles c JOIN c.students u JOIN StudentProgress sp ON sp.student.id = u.id GROUP BY m ORDER BY COUNT(DISTINCT sp) DESC")
    List<Object[]> findMostActiveMosques(Pageable pageable);

    // Location-based queries
    @Query("SELECT m FROM Mosque m WHERE m.location LIKE %:location%")
    List<Mosque> findByLocation(@Param("location") String location);

    @Query("SELECT m FROM Mosque m WHERE m.location LIKE %:location%")
    Page<Mosque> findByLocation(@Param("location") String location, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Circle c WHERE c.mosque.id = :mosqueId")
    Long countCirclesByMosqueId(@Param("mosqueId") Long mosqueId);
}