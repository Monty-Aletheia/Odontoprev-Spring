package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.service.PatientService;
import com.fiap.br.challenger.domain.model.Patient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String getAllPatients(Model model) {

        model.addAttribute("patients" ,patientService.getAllPatients());
        return "patients/list";
    }

    @GetMapping("/{id}")
    public String getPatientByUUID(@PathVariable UUID id) {
        patientService.getPatientByUUID(id);
        return "patients/list";
    }


    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "/patients/form";
    }

    @PostMapping("/add")
    public String createPatient(@ModelAttribute("patient") PatientRequestDTO patientRequestDTO) {
        patientService.createPatient(patientRequestDTO);
        return "/patients/list";
    }

    @PutMapping("/{id}")
    public String updatePatient(@PathVariable UUID id, @RequestBody PatientRequestDTO patientRequestDTO) {
        return "/patients/list";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
