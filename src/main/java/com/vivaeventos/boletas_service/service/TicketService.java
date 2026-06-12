package com.vivaeventos.boletas_service.service;

import com.vivaeventos.boletas_service.dto.CreateTicketRequest;
import com.vivaeventos.boletas_service.model.Ticket;
import com.vivaeventos.boletas_service.model.TicketStatus;
import com.vivaeventos.boletas_service.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository repository;
    private final QrCodeService qrCodeService;

    public Ticket createTicket(CreateTicketRequest request) {

        Ticket ticket = Ticket.builder()
                .id(UUID.randomUUID())
                .qrCode(UUID.randomUUID().toString())
                .eventId(request.eventId())
                .orderId(request.orderId())
                .attendeeId(request.attendeeId())
                .status(TicketStatus.ACTIVE)
                .build();

        return repository.save(ticket);
    }

    public Ticket getTicket(UUID id) {
        return repository.findById(id)
                .orElseThrow();
    }

    public List<Ticket> getAllTickets() {
        return repository.findAll();
    }

    public byte[] getQrCode(UUID ticketId) {

        Ticket ticket = repository.findById(ticketId)
                .orElseThrow();

        return qrCodeService.generateQrCode(
                ticket.getQrCode()
        );
    }
}