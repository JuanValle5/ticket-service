package com.vivaeventos.boletas_service.job;

import com.vivaeventos.boletas_service.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReminderEmailJob implements Job {

    private final EmailService emailService;

    @Override
    public void execute(
            JobExecutionContext context
    ) {

        String email =
                context.getMergedJobDataMap()
                        .getString("email");

        String eventName =
                context.getMergedJobDataMap()
                        .getString("eventName");

        log.info(
                "Enviando recordatorio para {}",
                email
        );

        emailService.sendEmailWithAttachments(
                email,
                "Recordatorio de evento",
                """
                Hola.

                Te recordamos que tienes un evento próximo:

                Evento: %s

                Nos vemos pronto.
                """.formatted(eventName),
                Map.of()
        );
    }
}