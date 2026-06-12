package com.vivaeventos.boletas_service.controller;

import com.vivaeventos.boletas_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class EmailTestController {

    private final EmailService emailService;
/*
    @PostMapping("/email")
    public String sendTestEmail() {

        emailService.sendEmail(
                "juanjo.valencia.j@gmail.com",
                "Prueba VivaEventos",
                "Si recibes este correo, la integración funciona."
        );

        return "Correo enviado";*/

}