package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.auth.UserRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.service.PatientService;
import com.fiap.br.challenger.application.service.UserService;
import com.fiap.br.challenger.domain.model.User;
import com.fiap.br.challenger.infra.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterController {

    private final UserService service;
    private final PatientService patientService;
    private final UserRepository userRepository;

    public RegisterController(UserService service, UserRepository userRepository, PatientService patientService) {
        this.service = service;
        this.userRepository = userRepository;
        this.patientService = patientService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model){
        model.addAttribute("patient", new PatientRequestDTO());
        return "redirect:/patients/new";
    }

    @PostMapping("/register/add")
    private String post(@ModelAttribute("patient") @Valid PatientRequestDTO dto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors());
            model.addAttribute("patient", dto);
            return "auth/register";
        }
        service.createUser(new User());
//        patientService.createPatient(new User());
        return "redirect:/login";
    }

}
