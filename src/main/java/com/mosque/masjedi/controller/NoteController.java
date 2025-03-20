package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.NoteRequest;
import com.mosque.masjedi.dto.response.NoteResponse;
import com.mosque.masjedi.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.mosque.masjedi.service.UserService;
import jakarta.validation.Valid;

/**
 * Controller for managing notes.
 */
@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    /**
     * Creates a new note.
     * 
     * @param request The NoteRequest containing note details
     * @param jwt     The JWT token of the authenticated user
     * @return ResponseEntity containing created NoteResponse with HTTP status 201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires ADMIN or TEACHER role
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<NoteResponse> createNote(
            @Valid @RequestBody NoteRequest request,
            @AuthenticationPrincipal Jwt jwt) {
        Long authorId = userService.getUserByUsername(jwt.getClaimAsString("preferred_username")).id();
        return new ResponseEntity<>(noteService.createNote(request, authorId), HttpStatus.CREATED);
    }

    /**
     * Retrieves a note by its ID.
     * 
     * @param id The ID of the note to retrieve
     * @return ResponseEntity containing NoteResponse
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If note not
     *                                                              found
     * @apiNote Requires ADMIN/TEACHER role or student ownership of the note
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or @securityUtils.isStudentOwnerOfNote(#id)")
    public ResponseEntity<NoteResponse> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    /**
     * Retrieves notes for a specific student with pagination.
     * 
     * @param studentId The ID of the student to filter notes
     * @param pageable  Pagination configuration
     * @return ResponseEntity containing Page of NoteResponses
     * @apiNote Requires ADMIN/TEACHER role or student access to their own data
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') or #studentId == authentication.principal.id")
    public ResponseEntity<Page<NoteResponse>> getNotesByStudentId(
            @PathVariable Long studentId, Pageable pageable) {
        return ResponseEntity.ok(noteService.getNotesByStudentId(studentId, pageable));
    }

    /**
     * Updates an existing note.
     * 
     * @param id      The ID of the note to update
     * @param request The NoteRequest containing updated details
     * @return ResponseEntity containing updated NoteResponse
     * @throws jakarta.validation.ValidationException               If request
     *                                                              validation fails
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If note not
     *                                                              found
     * @apiNote Requires ADMIN or TEACHER role and author ownership of the note
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') and @securityUtils.isAuthorOfNote(#id)")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable Long id, @Valid @RequestBody NoteRequest request) {
        return ResponseEntity.ok(noteService.updateNote(id, request));
    }

    /**
     * Deletes a note by its ID.
     * 
     * @param id The ID of the note to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires ADMIN or TEACHER role and author ownership of the note
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER') and @securityUtils.isAuthorOfNote(#id)")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }
}