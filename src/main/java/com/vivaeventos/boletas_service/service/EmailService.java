package com.vivaeventos.boletas_service.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmailWithAttachments(
            String to,
            String subject,
            String body,
            Map<String, byte[]> attachments
    ) {

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

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error enviando correo",
                    e
            );
        }
    }
}