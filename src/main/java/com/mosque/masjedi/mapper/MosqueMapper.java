package com.mosque.masjedi.mapper;

import com.mosque.masjedi.dto.request.MosqueRequest;
import com.mosque.masjedi.dto.response.MosqueResponse;
import com.mosque.masjedi.entity.Mosque;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MosqueMapper extends BaseMapper<Mosque, MosqueResponse, MosqueRequest> {

    @Override
    @Mapping(target = "numberOfCircles", expression = "java(mosque.getCircles() != null ? mosque.getCircles().size() : 0)")
    MosqueResponse toDto(Mosque mosque);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "circles", ignore = true)
    Mosque toEntity(MosqueRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "circles", ignore = true)
    void updateEntity(MosqueRequest request, @MappingTarget Mosque mosque);
}