package com.mosque.masjedi.mapper;

import com.mosque.masjedi.dto.request.NoteRequest;
import com.mosque.masjedi.dto.response.NoteResponse;
import com.mosque.masjedi.entity.Note;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoteMapper extends BaseMapper<Note, NoteResponse, NoteRequest> {
    @Override
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "text", source = "text")
    NoteResponse toDto(Note note);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    Note toEntity(NoteRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    void updateEntity(NoteRequest request, @MappingTarget Note note);
}