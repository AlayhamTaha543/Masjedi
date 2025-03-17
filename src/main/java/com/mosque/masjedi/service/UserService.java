package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.UserRequest;
import com.mosque.masjedi.dto.response.UserResponse;
import com.mosque.masjedi.entity.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    // Create operations
    UserResponse createUser(UserRequest request, UserRole role);

    // Read operations
    UserResponse getCurrentUser();

    UserResponse getUserById(Long id);

    UserResponse getUserByUsername(String username);

    Page<UserResponse> getUsersByRole(UserRole role, Pageable pageable);

    List<UserResponse> getStudentsByCircleId(Long circleId);

    UserResponse getTeacherByCircleId(Long circleId);

    List<UserResponse> getUsersByMosqueId(Long mosqueId);

    Page<UserResponse> getUsersByMosqueIdAndRole(Long mosqueId, UserRole role, Pageable pageable);

    List<UserResponse> getAvailableTeachers(Long mosqueId);

    List<UserResponse> getUnassignedStudentsByMosqueId(Long mosqueId);

    // Update operations
    UserResponse updateUser(Long id, UserRequest request);

    void changePassword(String currentPassword, String newPassword);

    // Delete operations
    void deleteUser(Long id);

    // Student management
    void transferStudentToCircle(Long studentId, Long newCircleId);
}