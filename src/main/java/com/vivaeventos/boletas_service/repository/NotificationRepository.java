package com.vivaeventos.boletas_service.repository;

import com.vivaeventos.boletas_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository
        extends JpaRepository<Notification, UUID> {
}