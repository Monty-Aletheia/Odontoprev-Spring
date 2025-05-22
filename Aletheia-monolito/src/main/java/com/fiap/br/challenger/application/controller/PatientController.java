package com.fiap.br.challenger.application.controller;

import com.fiap.br.challenger.application.dto.patient.PatientRequestDTO;
import com.fiap.br.challenger.application.dto.patient.PatientResponseDTO;
import com.fiap.br.challenger.application.dto.patient.PatientRiskAssessmentDTO;
import com.fiap.br.challenger.application.service.PatientService;
import com.fiap.br.challenger.domain.model.patient.PatientRiskAssessment;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.UUID;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/list")
    public String getAllPatients(Model model, Locale locale) {
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("lang", locale.getLanguage());
        return "patients/list";
    }

    @GetMapping("/new")
    public String showForm(HttpSession session, Model model) {

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth != null &&
                auth.isAuthenticated() &&
                !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"));

        if (!isLoggedIn) {
            return "redirect:/login";
        }
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

    @GetMapping("/{id}")
    public String detailsPage(@PathVariable UUID id, Locale locale, Model model) {
        PatientResponseDTO patient = patientService.getPatientByUUID(id);
        String lang = locale.getLanguage();

        if (patient.getAiReport() != null) {
            if (!patient.getAiReport().startsWith("{")){
                model.addAttribute("patient", patient);
                model.addAttribute("lang", lang);
                return "patients/details";
            }
            JSONObject jsonObject = new JSONObject(patient.getAiReport());

            if (lang.equals("en")) {
                patient.setAiReport(jsonObject.getString("english"));
            } else {
                patient.setAiReport(jsonObject.getString("portuguese"));
            }
        }

        model.addAttribute("patient", patient);
        model.addAttribute("lang", lang);
        return "patients/details";
    }
}
