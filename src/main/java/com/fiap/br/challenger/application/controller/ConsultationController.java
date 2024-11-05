package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.application.service.ConsultationService;
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
@RequestMapping("/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;

    @Autowired
    public ConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ConsultationResponseDTO>>> getAllConsultations() {
        List<EntityModel<ConsultationResponseDTO>> consultationModels = consultationService.getAllConsultations().stream()
                .map(this::toEntityModel)
                .toList();

        Link selfLink = linkTo(methodOn(ConsultationController.class).getAllConsultations()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(consultationModels, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultationResponseDTO>> getConsultationById(@PathVariable UUID id) {
        return consultationService.getConsultationByUUID(id)
                .map(this::toEntityModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<ConsultationResponseDTO>> createConsultation(@RequestBody @Valid ConsultationRequestDTO consultationRequestDTO) {
        try {
            ConsultationResponseDTO createdConsultation = consultationService.createConsultation(consultationRequestDTO);
            return new ResponseEntity<>(toEntityModel(createdConsultation), HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultationResponseDTO>> updateConsultation(
            @PathVariable UUID id,
            @RequestBody @Valid ConsultationRequestDTO consultationRequestDTO) {
        Optional<ConsultationResponseDTO> updatedConsultation = consultationService.updateConsultation(id, consultationRequestDTO);

        return updatedConsultation
                .map(this::toEntityModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable UUID id) {
        try {
            consultationService.deleteConsultation(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper methods

    private EntityModel<ConsultationResponseDTO> toEntityModel(ConsultationResponseDTO consultation) {
        EntityModel<ConsultationResponseDTO> model = EntityModel.of(consultation);
        model.add(linkTo(methodOn(ConsultationController.class).getConsultationById(consultation.getId())).withSelfRel());
        model.add(linkTo(methodOn(ConsultationController.class).getAllConsultations()).withRel("allConsultations"));
        model.add(linkTo(methodOn(ConsultationController.class).deleteConsultation(consultation.getId())).withRel("deleteConsultation"));
        return model;
    }
}
