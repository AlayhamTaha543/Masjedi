package com.mosque.masjedi.mapper;

import com.mosque.masjedi.dto.request.CourseRequest;
import com.mosque.masjedi.dto.response.CourseResponse;
import com.mosque.masjedi.entity.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper extends BaseMapper<Course, CourseResponse, CourseRequest> {
    @Override
    @Mapping(target = "numberOfLessons", expression = "java(course.getLessons() != null ? course.getLessons().size() : 0)")
    CourseResponse toDto(Course course);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    Course toEntity(CourseRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lessons", ignore = true)
    void updateEntity(CourseRequest request, @MappingTarget Course course);
}