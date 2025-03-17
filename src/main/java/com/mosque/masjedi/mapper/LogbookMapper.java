package com.mosque.masjedi.mapper;

import com.mosque.masjedi.dto.request.LogbookRequest;
import com.mosque.masjedi.dto.response.LogbookResponse;
import com.mosque.masjedi.entity.Logbook;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogbookMapper
        extends BaseMapper<Logbook, LogbookResponse, LogbookRequest> {
    @Override
    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "courseId", source = "course.id")
    LogbookResponse toDto(Logbook logbook);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    Logbook toEntity(LogbookRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    void updateEntity(LogbookRequest request, @MappingTarget Logbook logbook);

    default List<LogbookResponse> toDtoList(List<Logbook> logbooks) {
        if (logbooks == null) {
            return null;
        }
        return logbooks.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
