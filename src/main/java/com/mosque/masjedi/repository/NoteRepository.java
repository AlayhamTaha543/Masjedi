package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findByStudentId(Long studentId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Note n WHERE n.student.id = :studentId AND n.id = :noteId")
    void deleteByStudentAndNoteId(@Param("studentId") Long studentId, @Param("noteId") Long noteId);
}