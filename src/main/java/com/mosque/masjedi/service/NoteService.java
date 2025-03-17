package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.NoteRequest;
import com.mosque.masjedi.dto.response.NoteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoteService {
    // Create operations
    NoteResponse createNote(NoteRequest request, Long authorId);

    // Read operations
    NoteResponse getNoteById(Long id);

    Page<NoteResponse> getNotesByStudentId(Long studentId, Pageable pageable);

    // Update operations
    NoteResponse updateNote(Long id, NoteRequest request);

    // Delete operations
    void deleteNote(Long id);
}