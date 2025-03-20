package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.UserRequest;
import com.mosque.masjedi.dto.response.UserResponse;
import com.mosque.masjedi.entity.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for user-related operations.
 */
public interface UserService {
    // Create operations

    /**
     * Create a new user.
     *
     * @param request the user request
     * @param role    the role of the user
     * @return the created user response
     */
    UserResponse createUser(UserRequest request, UserRole role);

    // Read operations

    /**
     * Get the current logged-in user.
     *
     * @return the current user response
     */
    UserResponse getCurrentUser();

    /**
     * Get a user by ID.
     *
     * @param id the ID of the user
     * @return the user response
     */
    UserResponse getUserById(Long id);

    /**
     * Get a user by username.
     *
     * @param username the username of the user
     * @return the user response
     */
    UserResponse getUserByUsername(String username);

    /**
     * Get users by role.
     *
     * @param role     the role of the users
     * @param pageable the pagination information
     * @return the paginated list of user responses
     */
    Page<UserResponse> getUsersByRole(UserRole role, Pageable pageable);

    /**
     * Get students by circle ID.
     *
     * @param circleId the ID of the circle
     * @return the list of student responses
     */
    List<UserResponse> getStudentsByCircleId(Long circleId);

    /**
     * Get the teacher by circle ID.
     *
     * @param circleId the ID of the circle
     * @return the teacher response
     */
    UserResponse getTeacherByCircleId(Long circleId);

    /**
     * Get users by mosque ID.
     *
     * @param mosqueId the ID of the mosque
     * @return the list of user responses
     */
    List<UserResponse> getUsersByMosqueId(Long mosqueId);

    /**
     * Get users by mosque ID and role.
     *
     * @param mosqueId the ID of the mosque
     * @param role     the role of the users
     * @param pageable the pagination information
     * @return the paginated list of user responses
     */
    Page<UserResponse> getUsersByMosqueIdAndRole(Long mosqueId, UserRole role, Pageable pageable);

    /**
     * Get available teachers by mosque ID.
     *
     * @param mosqueId the ID of the mosque
     * @return the list of available teacher responses
     */
    List<UserResponse> getAvailableTeachers(Long mosqueId);

    /**
     * Get unassigned students by mosque ID.
     *
     * @param mosqueId the ID of the mosque
     * @return the list of unassigned student responses
     */
    List<UserResponse> getUnassignedStudentsByMosqueId(Long mosqueId);

    // Update operations

    /**
     * Update a user.
     *
     * @param id      the ID of the user
     * @param request the user request
     * @return the updated user response
     */
    UserResponse updateUser(Long id, UserRequest request);

    /**
     * Change the password of the current user.
     *
     * @param currentPassword the current password
     * @param newPassword     the new password
     */
    void changePassword(String currentPassword, String newPassword);

    // Delete operations

    /**
     * Delete a user by ID.
     *
     * @param id the ID of the user
     */
    void deleteUser(Long id);

    // Student management

    /**
     * Transfer a student to a new circle.
     *
     * @param studentId   the ID of the student
     * @param newCircleId the ID of the new circle
     */
    void transferStudentToCircle(Long studentId, Long newCircleId);
}