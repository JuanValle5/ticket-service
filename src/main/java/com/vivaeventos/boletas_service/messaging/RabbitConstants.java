package com.vivaeventos.boletas_service.messaging;

public class RabbitConstants {

    public static final String ORDER_CONFIRMED_QUEUE =
            "order.confirmed.queue";

    public static final String TICKET_EXCHANGE =
            "ticket.exchange";

    public static final String TICKET_CREATED_QUEUE =
            "ticket.created.queue";

    public static final String TICKET_CREATED_ROUTING_KEY =
            "ticket.created";

    private RabbitConstants() {
    }
}
