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
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    @Autowired
    private ClaimService claimService;

    @GetMapping
    public ResponseEntity<List<EntityModel<ClaimResponseDTO>>> listClaims() {
        List<ClaimResponseDTO> claims = claimService.getAllClaims();

        List<EntityModel<ClaimResponseDTO>> claimModels = claims.stream()
                .map(claim -> {
                    Link selfLink = linkTo(methodOn(ClaimController.class).getClaimById(claim.getId())).withSelfRel();
                    return EntityModel.of(claim, selfLink);
                })
                .toList();

        return ResponseEntity.ok(claimModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClaimResponseDTO>> getClaimById(@PathVariable UUID id) {
        Optional<ClaimResponseDTO> responseDTO = claimService.getClaimByUUID(id);

        if (responseDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ClaimResponseDTO claim = responseDTO.get();
        EntityModel<ClaimResponseDTO> model = EntityModel.of(claim);

        Link selfLink = linkTo(methodOn(ClaimController.class).getClaimById(id)).withSelfRel();
        model.add(selfLink);

        Link linkToAll = linkTo(methodOn(ClaimController.class).listClaims()).withRel("allClaims");
        model.add(linkToAll);

        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<ClaimResponseDTO>> createClaim(@RequestBody @Valid ClaimRequestDTO claimRequestDTO) {
        try {
            ClaimResponseDTO responseDTO = claimService.createClaim(claimRequestDTO);
            EntityModel<ClaimResponseDTO> model = EntityModel.of(responseDTO);

            Link selfLink = linkTo(methodOn(ClaimController.class).getClaimById(responseDTO.getId())).withSelfRel();
            model.add(selfLink);

            Link linkToAll = linkTo(methodOn(ClaimController.class).listClaims()).withRel("allClaims");
            model.add(linkToAll);

            return new ResponseEntity<>(model, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return  ResponseEntity.notFound().build();
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
}
