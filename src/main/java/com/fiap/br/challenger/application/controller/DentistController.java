package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.dentist.DentistRequestDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.service.DentistService;
import jakarta.persistence.EntityExistsException;
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
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/dentists")
public class DentistController {

    private final DentistService dentistService;

    @Autowired
    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DentistResponseDTO>>> getAllDentists() {
        List<EntityModel<DentistResponseDTO>> dentistModels = dentistService.getAllDentists().stream()
                .map(this::toEntityModel)
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(DentistController.class).getAllDentists()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(dentistModels, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DentistResponseDTO>> getDentistById(@PathVariable UUID id) {
        return dentistService.getDentistsByUUID(id)
                .map(this::toDetailedEntityModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<DentistResponseDTO>> createDentist(@Valid @RequestBody DentistRequestDTO request) {
        try {
            DentistResponseDTO createdDentist = dentistService.createDentist(request);
            EntityModel<DentistResponseDTO> model = toEntityModel(createdDentist);
            return ResponseEntity.status(HttpStatus.CREATED).body(model);
        } catch (EntityExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DentistResponseDTO>> updateDentist(
            @PathVariable UUID id,
            @Valid @RequestBody DentistRequestDTO request) {
        Optional<DentistResponseDTO> updatedDentist = dentistService.updateDentist(id, request);
        return updatedDentist
                .map(this::toEntityModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDentist(@PathVariable UUID id) {
        try {
            dentistService.deleteDentist(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Helper methods

    private EntityModel<DentistResponseDTO> toEntityModel(DentistResponseDTO dentist) {
        EntityModel<DentistResponseDTO> model = EntityModel.of(dentist);
        model.add(selfLink(dentist.getId()), allDentistsLink());
        return model;
    }

    private EntityModel<DentistResponseDTO> toDetailedEntityModel(DentistResponseDTO dentist) {
        EntityModel<DentistResponseDTO> model = toEntityModel(dentist);
        model.add(deleteLink(dentist.getId()));
        return model;
    }

    private Link selfLink(UUID id) {
        return linkTo(methodOn(DentistController.class).getDentistById(id)).withSelfRel();
    }

    private Link allDentistsLink() {
        return linkTo(methodOn(DentistController.class).getAllDentists()).withRel("allDentists");
    }

    private Link deleteLink(UUID id) {
        return linkTo(methodOn(DentistController.class).deleteDentist(id)).withRel("deleteDentist");
    }
}
