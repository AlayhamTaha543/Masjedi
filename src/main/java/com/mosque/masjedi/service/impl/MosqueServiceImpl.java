package com.mosque.masjedi.service.impl;

import com.mosque.masjedi.dto.request.MosqueRequest;
import com.mosque.masjedi.dto.response.MosqueResponse;
import com.mosque.masjedi.entity.Mosque;
import com.mosque.masjedi.exception.NotFoundException;
import com.mosque.masjedi.mapper.MosqueMapper;
import com.mosque.masjedi.repository.MosqueRepository;
import com.mosque.masjedi.service.MosqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MosqueServiceImpl implements MosqueService {
    private final MosqueRepository mosqueRepository;
    private final MosqueMapper mosqueMapper;

    @Override
    @Transactional
    public MosqueResponse createMosque(MosqueRequest request) {
        Mosque mosque = mosqueMapper.toEntity(request);
        mosque = mosqueRepository.save(mosque);
        return mosqueMapper.toDto(mosque);
    }

    @Override
    public MosqueResponse getMosqueById(Long id) {
        return mosqueRepository.findById(id)
                .map(mosqueMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Mosque not found"));
    }

    @Override
    public Page<MosqueResponse> getAllMosques(Pageable pageable) {
        return mosqueRepository.findAll(pageable)
                .map(mosqueMapper::toDto);
    }

    @Override
    public Page<MosqueResponse> searchMosquesByTitle(String title, Pageable pageable) {
        return mosqueRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(mosqueMapper::toDto);
    }

    @Override
    public Page<MosqueResponse> searchMosquesByLocation(String location, Pageable pageable) {
        return mosqueRepository.findByLocationContainingIgnoreCase(location, pageable)
                .map(mosqueMapper::toDto);
    }

    @Override
    public Long countCirclesByMosqueId(Long mosqueId) {
        return mosqueRepository.countCirclesByMosqueId(mosqueId);
    }

    @Override
    @Transactional
    public MosqueResponse updateMosque(Long id, MosqueRequest request) {
        Mosque mosque = mosqueRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mosque not found"));
        mosqueMapper.updateEntity(request, mosque);
        mosque = mosqueRepository.save(mosque);
        return mosqueMapper.toDto(mosque);
    }

    @Override
    @Transactional
    public void deleteMosque(Long id) {
        if (!mosqueRepository.existsById(id)) {
            throw new NotFoundException("Mosque not found");
        }
        mosqueRepository.deleteById(id);
    }
}