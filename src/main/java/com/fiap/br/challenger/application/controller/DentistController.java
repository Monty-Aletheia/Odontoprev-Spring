package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.dentist.DentistRequestDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.service.DentistService;
import com.fiap.br.challenger.domain.model.Dentist;
import com.fiap.br.challenger.domain.model.Patient;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/dentists")
public class  DentistController {

    private final DentistService dentistService;

    @Autowired
    public DentistController(DentistService dentistService) {
        this.dentistService = dentistService;
    }

    @GetMapping("/list")
    public String getAllDentists(Model model) {
        List<DentistResponseDTO> dentists = dentistService.getAllDentists();
        model.addAttribute("dentists", dentists);
        return "/dentists/list";
    }

    @GetMapping("/{id}")
    public String getDentistById(@PathVariable UUID id) {
        return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH";
    }


    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("dentist", new Dentist());
        return "/dentists/form";
    }


    @PostMapping
    public String createDentist(@Valid @ModelAttribute DentistRequestDTO dentistRequestDTO) {
        dentistService.createDentist(dentistRequestDTO);
        return "redirect:/list";
    }

    @PutMapping("/{id}")
    public String updateDentist(
            @PathVariable UUID id,
            @Valid @ModelAttribute DentistRequestDTO request) {
        return "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH";
    }

    @DeleteMapping("/{id}")
    public String deleteDentist(@PathVariable UUID id) {
        return "AAAAAAAAAAAAAAAAAAH";
    }

}
