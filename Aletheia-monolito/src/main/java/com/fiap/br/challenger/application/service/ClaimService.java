package com.fiap.br.challenger.application.service;

import com.fiap.br.challenger.application.dto.claim.ClaimResponseDTO;
import com.fiap.br.challenger.application.dto.claim.ClaimRequestDTO;
import com.fiap.br.challenger.application.service.mapper.ClaimMapper;
import com.fiap.br.challenger.domain.model.Claim;
import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.infra.repository.ClaimRepository;
import com.fiap.br.challenger.infra.repository.ConsultationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClaimService {

    private final ClaimRepository claimRepository;

    private final ConsultationRepository consultationRepository;

    private final ClaimMapper claimMapper;

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
        Consultation consultation = consultationRepository.findById(claimRequestDTO.consultationId())
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + claimRequestDTO.consultationId()));

        Claim claim = claimMapper.toEntity(claimRequestDTO);
        claim.setConsultation(consultation);

        Claim savedClaim = claimRepository.save(claim);
        return claimMapper.toDto(savedClaim);
    }

    @Transactional
    public Optional<ClaimResponseDTO> updateClaim(UUID id, ClaimRequestDTO claimRequestDTO) {
        Claim claimToUpdate = claimRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Claim not found: " + id));

        claimMapper.updateEntityFromDto(claimToUpdate, claimRequestDTO);

        if (claimRequestDTO.consultationId() != null) {
            Consultation consultation = consultationRepository.findById(claimRequestDTO.consultationId())
                    .orElseThrow(() -> new EntityNotFoundException("Consultation not found: " + claimRequestDTO.consultationId()));
            claimToUpdate.setConsultation(consultation);
        }

        Claim updatedClaim = claimRepository.save(claimToUpdate);
        return Optional.of(claimMapper.toDto(updatedClaim));
    }

    @Transactional
    public void deleteClaim(UUID id) {
        if (!claimRepository.existsById(id)) {
            throw new EntityNotFoundException("Claim does not exist: " + id);
        }
        claimRepository.deleteById(id);
    }
}
