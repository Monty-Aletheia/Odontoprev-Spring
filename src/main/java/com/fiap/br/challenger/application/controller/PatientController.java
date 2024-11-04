package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.service.PatientService;
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
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public ResponseEntity<List<EntityModel<PatientResponseDTO>>> getAllPatients() {
        List<PatientResponseDTO> patients = patientService.getAllPatients();

        List<EntityModel<PatientResponseDTO>> patientModels = patients.stream()
                .map(patient -> {
                    Link selfLink = linkTo(methodOn(PatientController.class).getPatientByUUID(patient.getId())).withSelfRel();
                    return EntityModel.of(patient, selfLink);
                })
                .toList();

        return ResponseEntity.ok(patientModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PatientResponseDTO>> getPatientByUUID(@PathVariable UUID id) {
        Optional<PatientResponseDTO> responseDTO = patientService.getPatientByUUID(id);

        if (responseDTO.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PatientResponseDTO patient = responseDTO.get();
        EntityModel<PatientResponseDTO> model = EntityModel.of(patient);

        Link selfLink = linkTo(methodOn(PatientController.class).getPatientByUUID(id)).withSelfRel();
        model.add(selfLink);

        Link linkToAll = linkTo(methodOn(PatientController.class).getAllPatients()).withRel("allPatients");
        model.add(linkToAll);

        Link linkToDelete = linkTo(methodOn(PatientController.class).deletePatient(id)).withRel("deletePatient");
        model.add(linkToDelete);

        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<PatientResponseDTO>> createPatient(@RequestBody PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO responseDTO = patientService.createPatient(patientRequestDTO);
        EntityModel<PatientResponseDTO> model = EntityModel.of(responseDTO);

        Link selfLink = linkTo(methodOn(PatientController.class).getPatientByUUID(responseDTO.getId())).withSelfRel();
        model.add(selfLink);

        Link linkToAll = linkTo(methodOn(PatientController.class).getAllPatients()).withRel("allPatients");
        model.add(linkToAll);

        return new ResponseEntity<>(model, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
