package com.mosque.masjedi.mapper;

import com.mosque.masjedi.dto.request.StudentProgressRequest;
import com.mosque.masjedi.dto.response.StudentProgressResponse;
import com.mosque.masjedi.entity.StudentProgress;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentProgressMapper
        extends BaseMapper<StudentProgress, StudentProgressResponse, StudentProgressRequest> {
    @Override
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "lessonId", source = "lesson.id")
    @Mapping(target = "lessonTitle", source = "lesson.title")
    StudentProgressResponse toDto(StudentProgress progress);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    StudentProgress toEntity(StudentProgressRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "lesson", ignore = true)
    void updateEntity(StudentProgressRequest request, @MappingTarget StudentProgress progress);
}