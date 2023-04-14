package com.example.test_work_4.scheduled;


import com.example.test_work_4.repository.PasteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PasteScheduler {
private final PasteRepository pasteRepository;

    public PasteScheduler(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }
    @Scheduled(fixedRateString ="${pastebin.timer.minutes}",timeUnit = TimeUnit.MINUTES)
    @Transactional
    public void deleteExpiredPastes() {
        log.info("deleteExpiredPastes work");
        pasteRepository.deleteAllByExpirationIsBefore(Instant.now());
    }
}