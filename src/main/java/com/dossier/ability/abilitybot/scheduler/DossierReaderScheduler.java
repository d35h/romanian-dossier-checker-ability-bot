package com.dossier.ability.abilitybot.scheduler;

import com.dossier.ability.abilitybot.domain.LastProcessingDate;
import com.dossier.ability.abilitybot.services.common.RomanianDossierCheckerService;
import com.dossier.ability.abilitybot.services.data.LastProcessingDateService;
import com.dossier.ability.abilitybot.utils.DateUtils;
import org.h2.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class DossierReaderScheduler {

    private LastProcessingDate dateOfLastProcessedFile;

    private final RomanianDossierCheckerService romanianDossierCheckerService;

    private final LastProcessingDateService lastProcessingDateService;

    @Autowired
    public DossierReaderScheduler(RomanianDossierCheckerService romanianDossierCheckerService,
                                  LastProcessingDateService lastProcessingDateService) {
        this.romanianDossierCheckerService = romanianDossierCheckerService;
        this.lastProcessingDateService = lastProcessingDateService;
        dateOfLastProcessedFile = lastProcessingDateService.findFirstByDateIsNotNull();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DossierReaderScheduler.class);

    @Scheduled(cron = "${settings.dossier.reader.scheduler.cron}")
    public void execute() {
        doExecute();
    }

    private void doExecute() {
        LOGGER.info("Dossier reader scheduler executed");
        romanianDossierCheckerService.processAndSaveDossierSubjects(dateOfLastProcessedFile.getDate());
        dateOfLastProcessedFile.setDate(Calendar.getInstance().getTime());
        lastProcessingDateService.save(dateOfLastProcessedFile);
        LOGGER.info("Dossier reader scheduler finished");
    }
}
