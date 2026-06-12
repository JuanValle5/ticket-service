package com.vivaeventos.boletas_service.messaging;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemMessage {

    private UUID ticketTypeId;

    private String ticketTypeName;

    private Integer quantity;
}
