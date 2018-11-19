package com.dossier.ability.abilitybot.services.common;

import com.dossier.ability.abilitybot.domain.Subject;
import com.dossier.ability.abilitybot.services.data.SubjectService;
import com.dossier.ability.abilitybot.services.handlers.SubjectHandler;
import com.dossier.ability.abilitybot.services.parsers.WebPageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.*;

@Service
public class RomanianDossierCheckerService {

    private final SubjectHandler subjectHandler;

    private final SubjectService subjectService;

    private final WebPageParser webPageParser;

    @Autowired
    public RomanianDossierCheckerService(SubjectHandler subjectHandler,
                                         SubjectService subjectService,
                                         WebPageParser webPageParser) {
        this.subjectHandler = subjectHandler;
        this.subjectService = subjectService;
        this.webPageParser = webPageParser;
    }

    public void processAndSaveDossierSubjects(Date dateOfLastProcessedFile) {
        webPageParser.getPdfLinks()
                .entrySet()
                .stream()
                .filter(entry -> isAfterDateOfLastProcessedFile(entry, dateOfLastProcessedFile))
                .forEach(this::processAndSaveDossiers);
    }

    private void processAndSaveDossiers(Map.Entry<Date, List<String>> entry) {
        final List<String> linksToDossier = entry.getValue();
        linksToDossier.forEach(this::processAndSaveDossiers);
    }

    private void processAndSaveDossiers(String link) {
        final List<Subject> subjects = subjectHandler.getDossierSubjectsByUri(URI.create(link));
        subjectService.saveAll(subjects);
    }

    private boolean isAfterDateOfLastProcessedFile(Map.Entry<Date, List<String>> entry, Date dateOfLastProcessedFile) {
        return entry.getKey().after(dateOfLastProcessedFile);
    }

}
