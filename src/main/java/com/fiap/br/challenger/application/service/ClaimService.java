package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.claim.ClaimResponseDTO;
import com.fiap.br.challenger.application.dto.claim.ClaimRequestDTO;
import com.fiap.br.challenger.application.service.mapper.ClaimMapper;
import com.fiap.br.challenger.domain.model.Claim;
import com.fiap.br.challenger.infra.repository.ClaimRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClaimService {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimMapper claimMapper;

    public List<ClaimResponseDTO> getAllClaims() {
        return claimRepository.findAll().stream()
                .map(claimMapper::toDto)
                .toList();
    }

    public Optional<ClaimResponseDTO> getClaimByUUID(UUID id) {
        return claimRepository.findById(id).map(claimMapper::toDto);
    }

    @Transactional
    public ClaimResponseDTO createClaim(ClaimRequestDTO claimRequestDTO) {
        Claim claim = claimMapper.toEntity(claimRequestDTO);
        Claim savedClaim = claimRepository.save(claim);
        return claimMapper.toDto(savedClaim);
    }

    public void deleteClaim(UUID id) {
        if (!claimRepository.existsById(id)) {
            throw new EntityNotFoundException("O sinistro não existe: " + id);
        }
        claimRepository.deleteById(id);
    }
}
