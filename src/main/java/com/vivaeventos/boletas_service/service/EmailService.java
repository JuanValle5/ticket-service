package com.vivaeventos.boletas_service.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetrySynchronizationManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Retryable(
            retryFor = Exception.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 3000)
    )
    public void sendEmailWithAttachments(
            String to,
            String subject,
            String body,
            Map<String, byte[]> attachments
    ) {

        Integer retryCount =
                RetrySynchronizationManager.getContext() != null
                        ? RetrySynchronizationManager
                        .getContext()
                        .getRetryCount()
                        : 0;

        log.info(
                "Intento de envío #{} para {}",
                retryCount + 1,
                to
        );

        try {

            MimeMessage mimeMessage =
                    mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            mimeMessage,
                            true
                    );

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);

            for (Map.Entry<String, byte[]> attachment :
                    attachments.entrySet()) {

                helper.addAttachment(
                        attachment.getKey(),
                        new ByteArrayResource(
                                attachment.getValue()
                        )
                );
            }

            mailSender.send(mimeMessage);

            log.info(
                    "Correo enviado exitosamente a {}",
                    to
            );

        } catch (Exception e) {

            log.error(
                    "Error enviando correo a {}: {}",
                    to,
                    e.getMessage()
            );

            throw new RuntimeException(
                    "Error enviando correo",
                    e
            );
        }
    }
}