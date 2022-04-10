package com.qpg.config;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTask {

    //@Scheduled(fixedDelay = 5000)
    //@SchedulerLock(name = "scheduledTaskName")
    public void scheduledTask() {
        // To assert that the lock is held (prevents misconfiguration errors)
        LockAssert.assertLocked();
        // do something
    }
}
