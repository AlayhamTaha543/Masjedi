package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.NoteRequest;
import com.mosque.masjedi.dto.response.NoteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for note-related operations.
 */
public interface NoteService {
    // Create operations

    /**
     * Create a new note.
     *
     * @param request  the note request
     * @param authorId the ID of the author
     * @return the created note response
     */
    NoteResponse createNote(NoteRequest request, Long authorId);

    // Read operations

    /**
     * Get a note by ID.
     *
     * @param id the ID of the note
     * @return the note response
     */
    NoteResponse getNoteById(Long id);

    /**
     * Get notes by student ID.
     *
     * @param studentId the ID of the student
     * @param pageable  the pagination information
     * @return the paginated list of note responses
     */
    Page<NoteResponse> getNotesByStudentId(Long studentId, Pageable pageable);

    // Update operations

    /**
     * Update a note.
     *
     * @param id      the ID of the note
     * @param request the note request
     * @return the updated note response
     */
    NoteResponse updateNote(Long id, NoteRequest request);

    // Delete operations

    /**
     * Delete a note by ID.
     *
     * @param id the ID of the note
     */
    void deleteNote(Long id);
}