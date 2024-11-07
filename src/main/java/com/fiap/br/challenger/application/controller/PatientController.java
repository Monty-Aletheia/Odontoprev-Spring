package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PatientResponseDTO>>> getAllPatients() {
        List<EntityModel<PatientResponseDTO>> patientModels = patientService.getAllPatients()
                .stream()
                .map(this::toEntityModel)
                .toList();

        Link selfLink = linkTo(methodOn(PatientController.class).getAllPatients()).withSelfRel();
        return ResponseEntity.ok(CollectionModel.of(patientModels, selfLink));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PatientResponseDTO>> getPatientByUUID(@PathVariable UUID id) {
        return patientService.getPatientByUUID(id)
                .map(this::toEntityModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EntityModel<PatientResponseDTO>> createPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO responseDTO = patientService.createPatient(patientRequestDTO);
        EntityModel<PatientResponseDTO> model = toEntityModel(responseDTO);
        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PatientResponseDTO>> updatePatient(
            @PathVariable UUID id,
            @RequestBody PatientRequestDTO patientRequestDTO) {

        try {
            return patientService.updatePatient(id, patientRequestDTO)
                    .map(this::toEntityModel)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    // Helper methods

    private EntityModel<PatientResponseDTO> toEntityModel(PatientResponseDTO patient) {
        UUID id = patient.getId();
        return EntityModel.of(patient,
                selfLink(id),
                allPatientsLink(),
                deletePatientLink(id));
    }

    private Link selfLink(UUID id) {
        return linkTo(methodOn(PatientController.class).getPatientByUUID(id)).withSelfRel();
    }

    private Link allPatientsLink() {
        return linkTo(methodOn(PatientController.class).getAllPatients()).withRel("allPatients");
    }

    private Link deletePatientLink(UUID id) {
        return linkTo(methodOn(PatientController.class).deletePatient(id)).withRel("deletePatient");
    }
}
