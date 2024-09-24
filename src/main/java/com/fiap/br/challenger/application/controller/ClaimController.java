package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.claim.ClaimResponseDTO;
import com.fiap.br.challenger.application.dto.claim.ClaimRequestDTO;
import com.fiap.br.challenger.application.service.ClaimService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @GetMapping
    public ResponseEntity<?> listClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClaimById(@PathVariable UUID id) {
        Optional<ClaimResponseDTO> responseDTO = claimService.getClaimByUUID(id);
        return responseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createClaim(@RequestBody @Valid ClaimRequestDTO claimRequestDTO) {
        ClaimResponseDTO responseDTO = claimService.createClaim(claimRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaim(@PathVariable UUID id) {
        try {
            claimService.deleteClaim(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
