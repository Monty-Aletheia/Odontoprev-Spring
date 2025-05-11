package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRiskAssessmentDTO;
import com.fiap.br.challenger.application.service.PatientService;
import com.fiap.br.challenger.domain.model.patient.Patient;
import com.fiap.br.challenger.domain.model.patient.PatientRiskAssessment;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
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
        model.addAttribute("patient", new PatientRequestDTO());
        return "patients/form";
    }

    @GetMapping("/risk-assessment")
    public String showRiskFrom(Model model, @ModelAttribute("patient") PatientRequestDTO patientRequest, HttpSession session) {
        if (patientRequest == null) {
            return "redirect:/patients/new";
        }
        PatientRiskAssessment patientRiskAssessment = new PatientRiskAssessment();
        patientRiskAssessment.setAge(Period.between(patientRequest.getBirthday(), LocalDate.now()).getYears());

        patientRiskAssessment.setGender(patientRequest.getGender().toString());

        session.setAttribute("patient", patientRequest);

        model.addAttribute("patientRiskAssessment", patientRiskAssessment);

        return "patients/riskAssessment";
    }

    @PostMapping("/save-temp")
    public String savePatientTemp(@ModelAttribute("patient") PatientRequestDTO patientRequestDTO,
                                  RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("patient", patientRequestDTO);
        return "redirect:/patients/risk-assessment";
    }

    @PostMapping("/add")
    public String createPatient(
            @ModelAttribute("patientRiskAssessment") PatientRiskAssessmentDTO patientRiskAssessmentDTO,
            HttpSession session) {
        PatientRequestDTO patientRequestDTO = (PatientRequestDTO) session.getAttribute("patient");
        patientService.createPatient(patientRequestDTO, patientRiskAssessmentDTO);
        session.removeAttribute("patient");
        return "redirect:list";
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
