package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.dentist.DentistRequestDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistResponseDTO;
import com.fiap.br.challenger.application.dto.dentist.DentistUpdateDTO;
import com.fiap.br.challenger.application.service.DentistService;
import com.fiap.br.challenger.domain.model.Dentist;
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


    @PostMapping("/add")
    public String createDentist(@Valid @ModelAttribute("dentist") DentistRequestDTO dentistRequestDTO) {
        dentistService.createDentist(dentistRequestDTO);
        return "redirect:list";
    }


    @GetMapping("/form/{uuid}")
    public String showUpdateForm(@PathVariable UUID uuid, Model model) {
        DentistResponseDTO dentist = dentistService.getDentistsByUUID(uuid);
        model.addAttribute("dentist", dentist);
        model.addAttribute("uuid", uuid);
        return "dentists/update";
    }


    @PostMapping("/update/{uuid}")
    public String updateDentist(
            @PathVariable UUID uuid,
            @Valid @ModelAttribute("dentist") DentistUpdateDTO dentistUpdateDTO) {
        dentistService.updateDentist(uuid, dentistUpdateDTO);
        return "redirect:/dentists/list";
    }

    @GetMapping("/delete/{uuid}")
    public String deleteDentist(@PathVariable UUID uuid) {
        dentistService.deleteDentist(uuid);
        return "redirect:/dentists/list";
    }

}
