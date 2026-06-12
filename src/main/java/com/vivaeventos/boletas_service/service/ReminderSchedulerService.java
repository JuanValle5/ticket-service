package com.vivaeventos.boletas_service.service;

import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReminderSchedulerService {

    private final Scheduler scheduler;

    public void scheduleReminder(
            String email,
            String eventName,
            LocalDateTime executionTime
    ) throws SchedulerException {

        JobDetail job =
                JobBuilder.newJob()
                        .ofType(
                                com.vivaeventos.boletas_service.job.ReminderEmailJob.class
                        )
                        .withIdentity(
                                UUID.randomUUID().toString()
                        )
                        .usingJobData(
                                "email",
                                email
                        )
                        .usingJobData(
                                "eventName",
                                eventName
                        )
                        .build();

        Trigger trigger =
                TriggerBuilder.newTrigger()
                        .startAt(
                                Date.from(
                                        executionTime
                                                .atZone(
                                                        ZoneId.systemDefault()
                                                )
                                                .toInstant()
                                )
                        )
                        .build();

        scheduler.scheduleJob(
                job,
                trigger
        );
    }
}