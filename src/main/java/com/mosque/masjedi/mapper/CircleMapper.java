package com.mosque.masjedi.mapper;

import com.mosque.masjedi.dto.request.CircleRequest;
import com.mosque.masjedi.dto.response.CircleResponse;
import com.mosque.masjedi.entity.Circle;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CircleMapper extends BaseMapper<Circle, CircleResponse, CircleRequest> {
    @Override
    @Mapping(target = "mosqueTitle", source = "mosque.title")
    @Mapping(target = "teacherName", source = "teacher.username")
    @Mapping(target = "mosqueId", source = "mosque.id")
    @Mapping(target = "teacherId", source = "teacher.id")
    @Mapping(target = "numberOfStudents", expression = "java(circle.getStudents() != null ? circle.getStudents().size() : 0)")
    CircleResponse toDto(Circle circle);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "mosque", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    Circle toEntity(CircleRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "mosque", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    void updateEntity(CircleRequest request, @MappingTarget Circle circle);
}