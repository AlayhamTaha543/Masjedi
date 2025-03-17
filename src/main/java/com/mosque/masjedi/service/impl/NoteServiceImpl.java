package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.request.NoteRequest;
import com.mosque.masjedi.dto.response.NoteResponse;
import com.mosque.masjedi.entity.Note;
import com.mosque.masjedi.entity.User;
import com.mosque.masjedi.exception.NotFoundException;
import com.mosque.masjedi.mapper.NoteMapper;
import com.mosque.masjedi.repository.NoteRepository;
import com.mosque.masjedi.repository.UserRepository;
import com.mosque.masjedi.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final NoteMapper noteMapper;

    @Override
    @Transactional
    public NoteResponse createNote(NoteRequest request, Long authorId) {
        User student = userRepository.findById(request.studentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Note note = noteMapper.toEntity(request);
        note.setStudent(student);

        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    @Override
    public NoteResponse getNoteById(Long id) {
        return noteRepository.findById(id)
                .map(noteMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Note not found"));
    }

    @Override
    public Page<NoteResponse> getNotesByStudentId(Long studentId, Pageable pageable) {
        return noteRepository.findByStudentId(studentId, pageable)
                .map(noteMapper::toDto);
    }

    @Override
    @Transactional
    public NoteResponse updateNote(Long id, NoteRequest request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Note not found"));

        User student = userRepository.findById(request.studentId())
                .orElseThrow(() -> new NotFoundException("Student not found"));

        noteMapper.updateEntity(request, note);
        note.setStudent(student);

        note = noteRepository.save(note);
        return noteMapper.toDto(note);
    }

    @Override
    @Transactional
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new NotFoundException("Note not found");
        }
        noteRepository.deleteById(id);
    }
}