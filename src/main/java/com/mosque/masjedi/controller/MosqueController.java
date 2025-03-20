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

/**
 * Controller for managing mosques.
 */
@RestController
@RequestMapping("/api/mosques")
@RequiredArgsConstructor
public class MosqueController {
    private final MosqueService mosqueService;

    /**
     * Creates a new mosque.
     * 
     * @param request The MosqueRequest containing mosque details
     * @return ResponseEntity containing created MosqueResponse with HTTP status 201
     * @throws jakarta.validation.ValidationException If request validation fails
     * @apiNote Requires ADMIN role
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MosqueResponse> createMosque(@Valid @RequestBody MosqueRequest request) {
        return new ResponseEntity<>(mosqueService.createMosque(request), HttpStatus.CREATED);
    }

    /**
     * Retrieves a mosque by its ID.
     * 
     * @param id The ID of the mosque to retrieve
     * @return ResponseEntity containing MosqueResponse
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If mosque not
     *                                                              found
     * @apiNote Requires authentication
     */
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MosqueResponse> getMosqueById(@PathVariable Long id) {
        return ResponseEntity.ok(mosqueService.getMosqueById(id));
    }

    /**
     * Retrieves paginated list of all mosques.
     * 
     * @param pageable Pagination configuration
     * @return ResponseEntity containing Page of MosqueResponses
     * @apiNote Requires authentication
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<MosqueResponse>> getAllMosques(Pageable pageable) {
        return ResponseEntity.ok(mosqueService.getAllMosques(pageable));
    }

    /**
     * Searches mosques by title with pagination.
     * 
     * @param title    Search term for mosque title
     * @param pageable Pagination configuration
     * @return ResponseEntity containing Page of MosqueResponses
     * @apiNote Requires authentication
     */
    @GetMapping("/search/title")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<MosqueResponse>> searchMosquesByTitle(
            @RequestParam String title, Pageable pageable) {
        return ResponseEntity.ok(mosqueService.searchMosquesByTitle(title, pageable));
    }

    /**
     * Searches mosques by location with pagination.
     * 
     * @param location Search term for mosque location
     * @param pageable Pagination configuration
     * @return ResponseEntity containing Page of MosqueResponses
     * @apiNote Requires authentication
     */
    @GetMapping("/search/location")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<MosqueResponse>> searchMosquesByLocation(
            @RequestParam String location, Pageable pageable) {
        return ResponseEntity.ok(mosqueService.searchMosquesByLocation(location, pageable));
    }

    /**
     * Counts the number of circles associated with a mosque.
     * 
     * @param id The ID of the mosque to count circles for
     * @return ResponseEntity containing the circle count
     * @apiNote Requires authentication
     */
    @GetMapping("/{id}/circles/count")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Long> countCirclesByMosqueId(@PathVariable Long id) {
        return ResponseEntity.ok(mosqueService.countCirclesByMosqueId(id));
    }

    /**
     * Updates an existing mosque.
     * 
     * @param id      The ID of the mosque to update
     * @param request The MosqueRequest containing updated details
     * @return ResponseEntity containing updated MosqueResponse
     * @throws jakarta.validation.ValidationException               If request
     *                                                              validation fails
     * @throws com.mosque.masjedi.exception.EntityNotFoundException If mosque not
     *                                                              found
     * @apiNote Requires ADMIN role
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MosqueResponse> updateMosque(
            @PathVariable Long id, @Valid @RequestBody MosqueRequest request) {
        return ResponseEntity.ok(mosqueService.updateMosque(id, request));
    }

    /**
     * Deletes a mosque by its ID.
     * 
     * @param id The ID of the mosque to delete
     * @return Empty ResponseEntity with HTTP status 204
     * @apiNote Requires ADMIN role
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMosque(@PathVariable Long id) {
        mosqueService.deleteMosque(id);
        return ResponseEntity.noContent().build();
    }
}