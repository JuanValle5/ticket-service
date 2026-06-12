package com.vivaeventos.boletas_service.messaging;

import com.vivaeventos.boletas_service.dto.CreateTicketRequest;
import com.vivaeventos.boletas_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConfirmedListener {

    private final TicketService ticketService;

    @RabbitListener(
            queues = RabbitConstants.ORDER_CONFIRMED_QUEUE
    )
    public void handle(
            OrderConfirmedMessage message
    ) {

        log.info(
                "Orden confirmada recibida: {}",
                message.getOrderId()
        );

        message.getItems().forEach(item -> {

            for (int i = 0; i < item.getQuantity(); i++) {

                ticketService.createTicket(
                        new CreateTicketRequest(
                                message.getEventId(),
                                message.getOrderId(),
                                message.getBuyerId()
                        )
                );
            }
        });
    }
}