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

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request,
            @RequestParam UserRole role) {
        return new ResponseEntity<>(userService.createUser(request, role), HttpStatus.CREATED);
    }

    @GetMapping("/current")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUsersByRole(
            @PathVariable UserRole role, Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByRole(role, pageable));
    }

    @GetMapping("/circle/{circleId}/students")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<List<UserResponse>> getStudentsByCircleId(@PathVariable Long circleId) {
        return ResponseEntity.ok(userService.getStudentsByCircleId(circleId));
    }

    @GetMapping("/circle/{circleId}/teacher")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getTeacherByCircleId(@PathVariable Long circleId) {
        return ResponseEntity.ok(userService.getTeacherByCircleId(circleId));
    }

    @GetMapping("/mosque/{mosqueId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUsersByMosqueId(@PathVariable Long mosqueId) {
        return ResponseEntity.ok(userService.getUsersByMosqueId(mosqueId));
    }

    @GetMapping("/mosque/{mosqueId}/role/{role}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getUsersByMosqueIdAndRole(
            @PathVariable Long mosqueId,
            @PathVariable UserRole role,
            Pageable pageable) {
        return ResponseEntity.ok(userService.getUsersByMosqueIdAndRole(mosqueId, role, pageable));
    }

    @GetMapping("/mosque/{mosqueId}/available-teachers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAvailableTeachers(@PathVariable Long mosqueId) {
        return ResponseEntity.ok(userService.getAvailableTeachers(mosqueId));
    }

    @GetMapping("/mosque/{mosqueId}/unassigned-students")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getUnassignedStudentsByMosqueId(@PathVariable Long mosqueId) {
        return ResponseEntity.ok(userService.getUnassignedStudentsByMosqueId(mosqueId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id, @Valid @RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(
            @RequestParam String currentPassword,
            @RequestParam String newPassword) {
        userService.changePassword(currentPassword, newPassword);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/student/{studentId}/circle/{circleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> transferStudentToCircle(
            @PathVariable Long studentId, @PathVariable Long circleId) {
        userService.transferStudentToCircle(studentId, circleId);
        return ResponseEntity.ok().build();
    }
}