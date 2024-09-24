package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.service.PatientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping()
    public ResponseEntity<?> listPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientByUUID(@PathVariable UUID id) {
        Optional<PatientResponseDTO> responseDTO = patientService.getPatientByUUID(id);
        return responseDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<?> createPatient(@RequestBody @Valid PatientRequestDTO patientRequestDTO) {
        PatientResponseDTO responseDTO = patientService.createPatient(patientRequestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable UUID id) {
        try {
            patientService.deletePatient(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
