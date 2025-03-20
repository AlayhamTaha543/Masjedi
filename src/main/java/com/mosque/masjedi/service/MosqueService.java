package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.MosqueRequest;
import com.mosque.masjedi.dto.response.MosqueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for mosque-related operations.
 */
public interface MosqueService {
    // Create operations

    /**
     * Create a new mosque.
     *
     * @param request the mosque request
     * @return the created mosque response
     */
    MosqueResponse createMosque(MosqueRequest request);

    // Read operations

    /**
     * Get a mosque by ID.
     *
     * @param id the ID of the mosque
     * @return the mosque response
     */
    MosqueResponse getMosqueById(Long id);

    /**
     * Get all mosques with pagination.
     *
     * @param pageable the pagination information
     * @return the paginated list of mosque responses
     */
    Page<MosqueResponse> getAllMosques(Pageable pageable);

    /**
     * Search mosques by title with pagination.
     *
     * @param title    the title of the mosque
     * @param pageable the pagination information
     * @return the paginated list of mosque responses
     */
    Page<MosqueResponse> searchMosquesByTitle(String title, Pageable pageable);

    /**
     * Search mosques by location with pagination.
     *
     * @param location the location of the mosque
     * @param pageable the pagination information
     * @return the paginated list of mosque responses
     */
    Page<MosqueResponse> searchMosquesByLocation(String location, Pageable pageable);

    /**
     * Count the number of circles in a mosque.
     *
     * @param mosqueId the ID of the mosque
     * @return the number of circles
     */
    Long countCirclesByMosqueId(Long mosqueId);

    // Update operations

    /**
     * Update a mosque.
     *
     * @param id      the ID of the mosque
     * @param request the mosque request
     * @return the updated mosque response
     */
    MosqueResponse updateMosque(Long id, MosqueRequest request);

    // Delete operations

    /**
     * Delete a mosque by ID.
     *
     * @param id the ID of the mosque
     */
    void deleteMosque(Long id);
}