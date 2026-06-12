package com.vivaeventos.boletas_service.service;

import com.vivaeventos.boletas_service.model.Notification;
import com.vivaeventos.boletas_service.model.NotificationStatus;
import com.vivaeventos.boletas_service.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;

    public void saveSuccess(
            UUID ticketId,
            String email
    ) {

        repository.save(
                Notification.builder()
                        .ticketId(ticketId)
                        .email(email)
                        .status(NotificationStatus.SENT)
                        .attempts(1)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }

    public void saveFailure(
            UUID ticketId,
            String email,
            String error
    ) {

        repository.save(
                Notification.builder()
                        .ticketId(ticketId)
                        .email(email)
                        .status(NotificationStatus.FAILED)
                        .attempts(1)
                        .errorMessage(error)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }
}