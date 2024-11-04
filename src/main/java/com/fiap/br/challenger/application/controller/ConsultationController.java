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
@RequestMapping("/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping
    public ResponseEntity<List<EntityModel<ConsultationResponseDTO>>> listConsultations() {
        List<ConsultationResponseDTO> consultations = consultationService.getAllConsultations();

        consultations.forEach(consultation -> {
            Link selfLink = linkTo(methodOn(ConsultationController.class).getConsultationById(consultation.getId())).withSelfRel();
            consultation.add(selfLink);
        });

        List<EntityModel<ConsultationResponseDTO>> consultationModels = consultations.stream()
                .map(consultation -> {
                    Link selfLink = linkTo(methodOn(ConsultationController.class).getConsultationById(consultation.getId())).withSelfRel();
                    return EntityModel.of(consultation, selfLink);
                })
                .toList();

        return ResponseEntity.ok(consultationModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ConsultationResponseDTO>> getConsultationById(@PathVariable UUID id) {
        Optional<ConsultationResponseDTO> consultation = consultationService.getConsultationByUUID(id);

        if (consultation.isPresent()) {
            ConsultationResponseDTO consultationDTO = consultation.get();
            EntityModel<ConsultationResponseDTO> model = EntityModel.of(consultationDTO);

            Link selfLink = linkTo(methodOn(ConsultationController.class).getConsultationById(id)).withSelfRel();
            model.add(selfLink);

            Link linkToAll = linkTo(methodOn(ConsultationController.class).listConsultations()).withRel("allConsultations");
            model.add(linkToAll);

            Link linkToDelete = linkTo(methodOn(ConsultationController.class).deleteConsultation(id)).withRel("deleteConsultation");
            model.add(linkToDelete);

            return ResponseEntity.ok(model);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<EntityModel<ConsultationResponseDTO>> createConsultation(@RequestBody @Valid ConsultationRequestDTO consultationRequestDTO) {
        try {
            ConsultationResponseDTO createdConsultation = consultationService.createConsultation(consultationRequestDTO);
            EntityModel<ConsultationResponseDTO> model = EntityModel.of(createdConsultation);

            Link selfLink = linkTo(methodOn(ConsultationController.class).getConsultationById(createdConsultation.getId())).withSelfRel();
            model.add(selfLink);

            Link linkToAll = linkTo(methodOn(ConsultationController.class).listConsultations()).withRel("allConsultations");
            model.add(linkToAll);

            return new ResponseEntity<>(model, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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
}
