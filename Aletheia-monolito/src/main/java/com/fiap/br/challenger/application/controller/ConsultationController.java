package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.consultation.ConsultationRequestDTO;
import com.fiap.br.challenger.application.dto.consultation.ConsultationResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.service.ConsultationService;
import com.fiap.br.challenger.application.service.DentistService;
import com.fiap.br.challenger.application.service.PatientService;
import com.fiap.br.challenger.domain.model.Consultation;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.User;
import com.fiap.br.challenger.domain.model.enums.Role;
import com.fiap.br.challenger.domain.model.patient.Patient;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/consultations")
public class ConsultationController {

    private final ConsultationService consultationService;
    private final PatientService patientService;
    private final DentistService dentistService;


    public ConsultationController(ConsultationService consultationService, PatientService patientService, DentistService dentistService) {
        this.consultationService = consultationService;
        this.patientService = patientService;
        this.dentistService = dentistService;
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("dentists", dentistService.getAllDentists());
        model.addAttribute("consultation", new Consultation());
        return "consultations/form";
    }

    @PostMapping("/save")
    public String saveConsultation(@ModelAttribute("consultation") ConsultationRequestDTO consultationRequestDTO) {
        consultationService.createConsultation(consultationRequestDTO);
        return "redirect:/consultations";
    }

    @GetMapping("")
    public String listConsultations(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean hasRoleAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (hasRoleAdmin) {
            model.addAttribute("consultations", consultationService.findAllConsultation());
            return "consultations/list";
        }

        User currentUser =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Role userRole = currentUser.getRole();
        model.addAttribute("consultations", consultationService.findByUserId(currentUser.getId(), userRole));
        return "consultations/list";
    }

    @GetMapping("/form/{uuid}")
    public String showUpdateForm(@PathVariable UUID uuid, Model model) {
        model.addAttribute("consultation", consultationService.findById(uuid));
        model.addAttribute("dentists", dentistService.getAllDentists());
        model.addAttribute("uuid", uuid);
        return "consultations/update";
    }


    @PostMapping("/update/{uuid}")
    public String updateConsultation(@PathVariable UUID uuid, @ModelAttribute ConsultationRequestDTO consultationRequestDTO) {
        consultationService.updateConsultation(uuid, consultationRequestDTO);
        return "redirect:/consultations";
    }

    @GetMapping("/delete/{uuid}")
    public String deleteConsultation(@PathVariable UUID uuid) {
        consultationService.deleteConsultation(uuid);
        return "redirect:/consultations";
    }

}
