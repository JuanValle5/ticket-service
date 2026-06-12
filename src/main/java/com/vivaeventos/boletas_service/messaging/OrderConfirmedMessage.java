package com.vivaeventos.boletas_service.messaging;

import lombok.*;

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

    private UUID eventId;

    private List<OrderItemMessage> items;
}
