package com.mosque.masjedi.dto.response;

import com.mosque.masjedi.entity.enums.UserRole;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String username,
        UserRole role,
        Long circleId,
        String circleTitle,
        Long mosqueId,
        String mosqueTitle,
        LocalDateTime createdAt) {
}