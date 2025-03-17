package com.mosque.masjedi.service;

import com.mosque.masjedi.dto.request.MosqueRequest;
import com.mosque.masjedi.dto.response.MosqueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MosqueService {
    // Create operations
    MosqueResponse createMosque(MosqueRequest request);

    // Read operations
    MosqueResponse getMosqueById(Long id);

    Page<MosqueResponse> getAllMosques(Pageable pageable);

    Page<MosqueResponse> searchMosquesByTitle(String title, Pageable pageable);

    Page<MosqueResponse> searchMosquesByLocation(String location, Pageable pageable);

    Long countCirclesByMosqueId(Long mosqueId);

    // Update operations
    MosqueResponse updateMosque(Long id, MosqueRequest request);

    // Delete operations
    void deleteMosque(Long id);
}