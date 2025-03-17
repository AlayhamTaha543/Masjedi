package com.mosque.masjedi.controller;

import com.mosque.masjedi.dto.request.MosqueRequest;
import com.mosque.masjedi.dto.response.MosqueResponse;
import com.mosque.masjedi.service.MosqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/mosques")
@RequiredArgsConstructor
public class MosqueController {
    private final MosqueService mosqueService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MosqueResponse> createMosque(@Valid @RequestBody MosqueRequest request) {
        return new ResponseEntity<>(mosqueService.createMosque(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MosqueResponse> getMosqueById(@PathVariable Long id) {
        return ResponseEntity.ok(mosqueService.getMosqueById(id));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<MosqueResponse>> getAllMosques(Pageable pageable) {
        return ResponseEntity.ok(mosqueService.getAllMosques(pageable));
    }

    @GetMapping("/search/title")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<MosqueResponse>> searchMosquesByTitle(
            @RequestParam String title, Pageable pageable) {
        return ResponseEntity.ok(mosqueService.searchMosquesByTitle(title, pageable));
    }

    @GetMapping("/search/location")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<MosqueResponse>> searchMosquesByLocation(
            @RequestParam String location, Pageable pageable) {
        return ResponseEntity.ok(mosqueService.searchMosquesByLocation(location, pageable));
    }

    @GetMapping("/{id}/circles/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> countCirclesByMosqueId(@PathVariable Long id) {
        return ResponseEntity.ok(mosqueService.countCirclesByMosqueId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MosqueResponse> updateMosque(
            @PathVariable Long id, @Valid @RequestBody MosqueRequest request) {
        return ResponseEntity.ok(mosqueService.updateMosque(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMosque(@PathVariable Long id) {
        mosqueService.deleteMosque(id);
        return ResponseEntity.noContent().build();
    }
}