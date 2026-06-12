package com.vivaeventos.boletas_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String qrCode;

    @Column(nullable = false)
    private UUID eventId;

    @Column(nullable = false)
    private UUID orderId;

    @Column(nullable = false)
    private UUID attendeeId;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private LocalDateTime usedAt;

    private LocalDateTime entryRegisteredAt;
}
