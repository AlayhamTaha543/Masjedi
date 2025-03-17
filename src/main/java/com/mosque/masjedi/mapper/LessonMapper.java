package com.mosque.masjedi.mapper;

import com.mosque.masjedi.dto.request.LessonRequest;
import com.mosque.masjedi.dto.response.LessonResponse;
import com.mosque.masjedi.entity.Lesson;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LessonMapper extends BaseMapper<Lesson, LessonResponse, LessonRequest> {
    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "createdAt", source = "createdAt")
    LessonResponse toDto(Lesson lesson);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "course", ignore = true)
    Lesson toEntity(LessonRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "course", ignore = true)
    void updateEntity(LessonRequest request, @MappingTarget Lesson lesson);
}