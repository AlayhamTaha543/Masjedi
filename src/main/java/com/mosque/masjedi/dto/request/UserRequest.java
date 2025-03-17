package com.mosque.masjedi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.mosque.masjedi.entity.enums.UserRole;

public record UserRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password,
        UserRole role,
        Long circleId,
        Long mosqueId) {
}