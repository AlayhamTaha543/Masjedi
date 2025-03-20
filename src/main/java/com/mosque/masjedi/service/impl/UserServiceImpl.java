package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.request.UserRequest;
import com.mosque.masjedi.dto.response.UserResponse;
import com.mosque.masjedi.entity.User;
import com.mosque.masjedi.entity.enums.UserRole;
import com.mosque.masjedi.exception.NotFoundException;
import com.mosque.masjedi.exception.UnauthorizedException;
import com.mosque.masjedi.mapper.UserMapper;
import com.mosque.masjedi.repository.UserRepository;
import com.mosque.masjedi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the UserService interface.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Creates a new user.
     *
     * @param request the user request
     * @param role    the user role
     * @return the created user response
     */
    @Override
    @Transactional
    public UserResponse createUser(UserRequest request, UserRole role) {
        User user = userMapper.toEntity(request);
        user.setRole(role);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Gets the current authenticated user.
     *
     * @return the current user response
     * @throws UnauthorizedException if the user is not found
     */
    @Override
    public UserResponse getCurrentUser() {
        String username = getCurrentUsername();
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new UnauthorizedException("User not found"));
    }

    /**
     * Gets a user by its ID.
     *
     * @param id the user ID
     * @return the user response
     * @throws NotFoundException if the user is not found
     */
    @Override
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    /**
     * Gets a user by its username.
     *
     * @param username the username
     * @return the user response
     * @throws NotFoundException if the user is not found
     */
    @Override
    public UserResponse getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    /**
     * Gets users by role with pagination.
     *
     * @param role     the user role
     * @param pageable the pagination information
     * @return the paginated user responses
     */
    @Override
    public Page<UserResponse> getUsersByRole(UserRole role, Pageable pageable) {
        return userRepository.findByRole(role, pageable)
                .map(userMapper::toDto);
    }

    /**
     * Gets students by circle ID.
     *
     * @param circleId the circle ID
     * @return the list of user responses
     */
    @Override
    public List<UserResponse> getStudentsByCircleId(Long circleId) {
        return userRepository.findStudentsByCircleId(circleId, UserRole.STUDENT)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets the teacher by circle ID.
     *
     * @param circleId the circle ID
     * @return the user response
     */
    @Override
    public UserResponse getTeacherByCircleId(Long circleId) {
        return userRepository.findTeacherByCircleId(circleId, UserRole.TEACHER)
                .map(userMapper::toDto)
                .orElse(null);
    }

    /**
     * Gets users by mosque ID.
     *
     * @param mosqueId the mosque ID
     * @return the list of user responses
     */
    @Override
    public List<UserResponse> getUsersByMosqueId(Long mosqueId) {
        return userRepository.findByMosqueId(mosqueId)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets users by mosque ID and role with pagination.
     *
     * @param mosqueId the mosque ID
     * @param role     the user role
     * @param pageable the pagination information
     * @return the paginated user responses
     */
    @Override
    public Page<UserResponse> getUsersByMosqueIdAndRole(Long mosqueId, UserRole role, Pageable pageable) {
        return userRepository.findByMosqueIdAndRole(mosqueId, role, pageable)
                .map(userMapper::toDto);
    }

    /**
     * Gets available teachers by mosque ID.
     *
     * @param mosqueId the mosque ID
     * @return the list of user responses
     */
    @Override
    public List<UserResponse> getAvailableTeachers(Long mosqueId) {
        return userRepository.findAvailableTeachers(mosqueId, UserRole.TEACHER)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Gets unassigned students by mosque ID.
     *
     * @param mosqueId the mosque ID
     * @return the list of user responses
     */
    @Override
    public List<UserResponse> getUnassignedStudentsByMosqueId(Long mosqueId) {
        return userRepository.findUnassignedStudentsByMosqueId(mosqueId, UserRole.STUDENT)
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Updates a user.
     *
     * @param id      the user ID
     * @param request the user request
     * @return the updated user response
     * @throws NotFoundException if the user is not found
     */
    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        userMapper.updateEntity(request, user);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    /**
     * Changes the password of the current user.
     *
     * @param currentPassword the current password
     * @param newPassword     the new password
     * @throws UnsupportedOperationException if the method is not implemented
     */
    @Override
    @Transactional
    public void changePassword(String currentPassword, String newPassword) {
        // This would require integration with Keycloak
        // Implementation depends on whether you're using the Keycloak Admin Client
        throw new UnsupportedOperationException("Not implemented yet - requires Keycloak Admin Client");
    }

    /**
     * Deletes a user.
     *
     * @param id the user ID
     * @throws NotFoundException if the user is not found
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Transfers a student to a new circle.
     *
     * @param studentId   the student ID
     * @param newCircleId the new circle ID
     */
    @Override
    @Transactional
    public void transferStudentToCircle(Long studentId, Long newCircleId) {
        userRepository.transferStudentToCircle(studentId, newCircleId, UserRole.STUDENT);
    }

    /**
     * Gets the current username from the security context.
     *
     * @return the current username
     * @throws UnauthorizedException if the user information cannot be extracted
     *                               from the token
     */
    private String getCurrentUsername() {
        try {
            Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return jwt.getClaimAsString("preferred_username");
        } catch (Exception e) {
            throw new UnauthorizedException("Could not extract user information from token");
        }
    }
}