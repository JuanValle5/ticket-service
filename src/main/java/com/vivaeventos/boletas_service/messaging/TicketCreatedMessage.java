package com.vivaeventos.boletas_service.messaging;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketCreatedMessage {

    private UUID ticketId;

    private String qrCode;

    private UUID eventId;

    private UUID orderId;

    private UUID attendeeId;

    private String status;
}