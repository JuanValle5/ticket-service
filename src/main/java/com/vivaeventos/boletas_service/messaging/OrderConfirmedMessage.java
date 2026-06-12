package com.vivaeventos.boletas_service.messaging;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfirmedMessage {

    private UUID orderId;

    private UUID buyerId;

    private String buyerEmail;

    private UUID eventId;

    private String eventName;

    private LocalDate eventDate;

    private String eventLocation;

    private String eventCity;

    private List<OrderItemMessage> items;
}
