package com.vivaeventos.boletas_service.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishTicketCreated(
            TicketCreatedMessage message
    ) {

        rabbitTemplate.convertAndSend(
                RabbitConstants.TICKET_EXCHANGE,
                RabbitConstants.TICKET_CREATED_ROUTING_KEY,
                message
        );
    }
}