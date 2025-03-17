package com.mosque.masjedi.mapper;

import com.mosque.masjedi.dto.request.UserRequest;
import com.mosque.masjedi.dto.response.UserResponse;
import com.mosque.masjedi.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<User, UserResponse, UserRequest> {
    @Override
    @Mapping(target = "circleId", source = "circle.id")
    @Mapping(target = "mosqueId", source = "mosque.id")
    UserResponse toDto(User user);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "circle", ignore = true)
    @Mapping(target = "mosque", ignore = true)
    @Mapping(target = "taughtCircles", ignore = true)
    User toEntity(UserRequest request);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "circle", ignore = true)
    @Mapping(target = "mosque", ignore = true)
    @Mapping(target = "taughtCircles", ignore = true)
    void updateEntity(UserRequest request, @MappingTarget User user);
}