package com.fiap.br.challenger.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @GetMapping
    public String redirectToHome(){
        return "redirect:home";
    }

    @GetMapping("/home")
    public String showHome(){
        return "home";
    }
}
