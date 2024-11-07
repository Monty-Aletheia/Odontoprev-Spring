package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.claim.ClaimResponseDTO;
import com.fiap.br.challenger.application.dto.claim.ClaimRequestDTO;
import com.fiap.br.challenger.application.service.ClaimService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    private final ClaimService claimService;

    @Autowired
    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ClaimResponseDTO>>> listClaims() {
        List<EntityModel<ClaimResponseDTO>> claimModels = claimService.getAllClaims().stream()
                .map(this::toEntityModel)
                .toList();
        Link selfLink = linkTo(methodOn(ClaimController.class).listClaims()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(claimModels, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClaimResponseDTO>> getClaimById(@PathVariable UUID id) {
        return claimService.getClaimByUUID(id)
                .map(this::toEntityModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<ClaimResponseDTO>> createClaim(@RequestBody @Valid ClaimRequestDTO claimRequestDTO) {
        try {
            ClaimResponseDTO responseDTO = claimService.createClaim(claimRequestDTO);
            return new ResponseEntity<>(toEntityModel(responseDTO), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClaimResponseDTO>> updateClaim(
            @PathVariable UUID id,
            @RequestBody @Valid ClaimRequestDTO claimRequestDTO) {

        try {
            Optional<ClaimResponseDTO> updatedClaim = claimService.updateClaim(id, claimRequestDTO);

            return updatedClaim
                    .map(this::toEntityModel)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable UUID id) {
        try {
            claimService.deleteClaim(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<ClaimResponseDTO> toEntityModel(ClaimResponseDTO claim) {
        EntityModel<ClaimResponseDTO> model = EntityModel.of(claim);
        model.add(linkTo(methodOn(ClaimController.class).getClaimById(claim.getId())).withSelfRel());
        model.add(linkTo(methodOn(ClaimController.class).listClaims()).withRel("allClaims"));
        return model;
    }
}
