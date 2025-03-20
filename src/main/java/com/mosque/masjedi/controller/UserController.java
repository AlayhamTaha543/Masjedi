package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.UserRequest;
import com.mosque.masjedi.dto.response.UserResponse;
import com.mosque.masjedi.entity.enums.UserRole;
import com.mosque.masjedi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Controller for managing users.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Creates a new user.
     * 
     * @param request The UserRequest containing user details
     * @param role    The role of the new user
     * @return ResponseEntity containing created UserResponse with HTTP status 201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires ADMIN role
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request,
            @RequestParam UserRole role) {
        return new ResponseEntity<>(userService.createUser(request, role), HttpStatus.CREATED);
    }

    /**
     * Retrieves the current authenticated user.
     * 
     * @return ResponseEntity containing UserResponse
     * @apiNote Requires authentication
     */
    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param id The ID of the user to retrieve
     * @return ResponseEntity containing UserResponse
     * @throws com.mosque.masjedi.exception.NotFoundException If user not found
     * @apiNote Requires ADMIN role or user access to their own data
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    /**
     * Retrieves a user by their username.
     * 
     * @param username The username of the user to retrieve
     * @return ResponseEntity containing UserResponse
     * @throws com.mosque.masjedi.exception.NotFoundException If user not found
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    /**
     * Retrieves users by their role with pagination.
     * 
     * @param role     The role to filter users
     * @param pageable Pagination configuration
     * @return ResponseEntity containing Page of UserResponses
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUsersByRole(
            @PathVariable UserRole role, Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByRole(role, pageable));
    }

    /**
     * Retrieves students by their circle ID.
     * 
     * @param circleId The ID of the circle to filter students
     * @return ResponseEntity containing List of UserResponses
     * @apiNote Requires ADMIN or TEACHER role
     */
    @GetMapping("/circle/{circleId}/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<UserResponse>> getStudentsByCircleId(@PathVariable Long circleId) {
        return ResponseEntity.ok(userService.getStudentsByCircleId(circleId));
    }

    /**
     * Retrieves the teacher by their circle ID.
     * 
     * @param circleId The ID of the circle to filter teacher
     * @return ResponseEntity containing UserResponse
     * @apiNote Requires authentication
     */
    @GetMapping("/circle/{circleId}/teacher")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getTeacherByCircleId(@PathVariable Long circleId) {
        return ResponseEntity.ok(userService.getTeacherByCircleId(circleId));
    }

    /**
     * Retrieves users by their mosque ID.
     * 
     * @param mosqueId The ID of the mosque to filter users
     * @return ResponseEntity containing List of UserResponses
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/mosque/{mosqueId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsersByMosqueId(@PathVariable Long mosqueId) {
        return ResponseEntity.ok(userService.getUsersByMosqueId(mosqueId));
    }

    /**
     * Retrieves users by their mosque ID and role with pagination.
     * 
     * @param mosqueId The ID of the mosque to filter users
     * @param role     The role to filter users
     * @param pageable Pagination configuration
     * @return ResponseEntity containing Page of UserResponses
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/mosque/{mosqueId}/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUsersByMosqueIdAndRole(
            @PathVariable Long mosqueId,
            @PathVariable UserRole role,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByMosqueIdAndRole(mosqueId, role, pageable));
    }

    /**
     * Retrieves available teachers by their mosque ID.
     * 
     * @param mosqueId The ID of the mosque to filter available teachers
     * @return ResponseEntity containing List of UserResponses
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/mosque/{mosqueId}/available-teachers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAvailableTeachers(@PathVariable Long mosqueId) {
        return ResponseEntity.ok(userService.getAvailableTeachers(mosqueId));
    }

    /**
     * Retrieves unassigned students by their mosque ID.
     * 
     * @param mosqueId The ID of the mosque to filter unassigned students
     * @return ResponseEntity containing List of UserResponses
     * @apiNote Requires ADMIN role
     */
    @GetMapping("/mosque/{mosqueId}/unassigned-students")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUnassignedStudentsByMosqueId(@PathVariable Long mosqueId) {
        return ResponseEntity.ok(userService.getUnassignedStudentsByMosqueId(mosqueId));
    }

    /**
     * Updates an existing user.
     * 
     * @param id      The ID of the user to update
     * @param request The UserRequest containing updated details
     * @return ResponseEntity containing updated UserResponse
     * @throws jakarta.validation.ValidationException         If request validation
     *                                                        fails
     * @throws com.mosque.masjedi.exception.NotFoundException If user not found
     * @apiNote Requires ADMIN role or user access to their own data
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    /**
     * Changes the password of the current authenticated user.
     * 
     * @param currentPassword The current password
     * @param newPassword     The new password
     * @return Empty ResponseEntity with HTTP status 200
     * @apiNote Requires authentication
     */
    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        userService.changePassword(currentPassword, newPassword);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a user by their ID.
     * 
     * @param id The ID of the user to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires ADMIN role
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Transfers a student to a different circle.
     * 
     * @param studentId The ID of the student to transfer
     * @param circleId  The ID of the new circle
     * @return Empty ResponseEntity with HTTP status 200
     * @apiNote Requires ADMIN role
     */
    @PutMapping("/student/{studentId}/circle/{circleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> transferStudentToCircle(
            @PathVariable Long studentId, @PathVariable Long circleId) {
        userService.transferStudentToCircle(studentId, circleId);
        return ResponseEntity.ok().build();
    }
}