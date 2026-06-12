package com.vivaeventos.boletas_service.messaging;

import com.vivaeventos.boletas_service.dto.CreateTicketRequest;
import com.vivaeventos.boletas_service.service.NotificationService;
import com.vivaeventos.boletas_service.service.QrCodeService;
import com.vivaeventos.boletas_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.vivaeventos.boletas_service.model.Ticket;
import com.vivaeventos.boletas_service.service.EmailService;
import com.vivaeventos.boletas_service.model.Ticket;

import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConfirmedListener {

    private final TicketService ticketService;
    private final NotificationService notificationService;
    private final EmailService emailService;
    private final QrCodeService qrCodeService;

    @RabbitListener(
            queues = RabbitConstants.ORDER_CONFIRMED_QUEUE
    )
    public void handle(
            OrderConfirmedMessage message
    ) {

        log.info(
                "Orden confirmada recibida: {} email={}",
                message.getOrderId(),
                message.getBuyerEmail()
        );

        List<Ticket> generatedTickets =
                new ArrayList<>();

        message.getItems().forEach(item -> {

            for (int i = 0; i < item.getQuantity(); i++) {

                Ticket ticket =
                        ticketService.createTicket(
                                new CreateTicketRequest(
                                        message.getEventId(),
                                        message.getOrderId(),
                                        message.getBuyerId()
                                )
                        );

                generatedTickets.add(ticket);
            }
        });

        StringBuilder body =
                new StringBuilder();

        body.append("Hola.\n\n");
        body.append("Tu compra fue confirmada.\n\n");
        body.append("Orden: ")
                .append(message.getOrderId())
                .append("\n\n");

        body.append("Boletas generadas:\n");

        generatedTickets.forEach(ticket ->
                body.append("- ")
                        .append(ticket.getId())
                        .append("\n")
        );

        body.append("\nGracias por usar VivaEventos.");


        Map<String, byte[]> attachments =
                new HashMap<>();

        for (Ticket ticket : generatedTickets) {

            attachments.put(
                    "ticket-" +
                            ticket.getId() +
                            ".png",

                    qrCodeService.generateQrCode(
                            ticket.getQrCode()
                    )
            );
        }


        try {

            emailService.sendEmailWithAttachments(
                    message.getBuyerEmail(),
                    "Tus boletas de VivaEventos",
                    body.toString(),
                    attachments
            );

            generatedTickets.forEach(ticket ->
                    notificationService.saveSuccess(
                            ticket.getId(),
                            message.getBuyerEmail()
                    )
            );

        } catch (Exception e) {

            generatedTickets.forEach(ticket ->
                    notificationService.saveFailure(
                            ticket.getId(),
                            message.getBuyerEmail(),
                            e.getMessage()
                    )
            );

            throw e;
        }
    }
}