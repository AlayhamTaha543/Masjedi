package com.mosque.masjedi.mapper;

import org.mapstruct.MappingTarget;

public interface BaseMapper<E, R, Q> {
    R toDto(E entity);

    E toEntity(Q request);

    void updateEntity(Q request, @MappingTarget E entity);
}