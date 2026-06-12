package com.vivaeventos.boletas_service.controller;

import com.vivaeventos.boletas_service.dto.CreateTicketRequest;
import com.vivaeventos.boletas_service.model.Ticket;
import com.vivaeventos.boletas_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor

public class TicketController {
    private final TicketService service;

    @PostMapping
    public Ticket createTicket(
            @RequestBody CreateTicketRequest request
    ) {
        return service.createTicket(request);
    }

    @GetMapping("/{id}")
    public Ticket getTicket(
            @PathVariable UUID id
    ) {
        return service.getTicket(id);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return service.getAllTickets();
    }

    @GetMapping(
            value = "/{id}/qr",
            produces = MediaType.IMAGE_PNG_VALUE
    )
    public ResponseEntity<byte[]> getQrCode(
            @PathVariable UUID id
    ) {

        return ResponseEntity.ok(
                service.getQrCode(id)
        );
    }
}
