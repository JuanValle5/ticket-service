package com.vivaeventos.boletas_service.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderConfirmedListener {

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
    }
}