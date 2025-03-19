package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRiskAssessmentDTO;
import com.fiap.br.challenger.application.service.PatientService;
import com.fiap.br.challenger.domain.model.Patient;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/list")
    public String getAllPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
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
        return "redirect:list";
    }

    //TODO: FAZER O FORMULARIO DE AVALIAÇÃO DE RISCO
    @GetMapping("/risk")
    public String collectPatientRiskCalculationData(@ModelAttribute("form")PatientRiskAssessmentDTO patientRiskAssessmentDTO){

        return "redirect:add";
    }

    @GetMapping("/form/{uuid}")
    public String showUpdateForm(@PathVariable UUID uuid, Model model) {
        PatientResponseDTO patient = patientService.getPatientByUUID(uuid);
        model.addAttribute("patient", patient);
        model.addAttribute("uuid", uuid);
        return "patients/update";
    }


    @PostMapping("/update/{uuid}")
    public String updatePatient(@PathVariable UUID uuid, @ModelAttribute PatientRequestDTO patientRequestDTO) {
        patientService.updatePatient(uuid, patientRequestDTO);
        return "redirect:/patients/list";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return "redirect:/patients/list";
    }

}
