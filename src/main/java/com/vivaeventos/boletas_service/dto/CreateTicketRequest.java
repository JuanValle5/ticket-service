package com.vivaeventos.boletas_service.dto;

import java.util.UUID;

public record CreateTicketRequest(
        UUID eventId,
        UUID orderId,
        UUID attendeeId
) {
}
