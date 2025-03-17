package com.mosque.masjedi.repository;

import com.mosque.masjedi.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Page<Note> findByStudentId(Long studentId, Pageable pageable);
}