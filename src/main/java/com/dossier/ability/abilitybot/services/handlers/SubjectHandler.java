package com.dossier.ability.abilitybot.services.handlers;

import com.dossier.ability.abilitybot.domain.Subject;

import java.net.URI;
import java.util.List;

public interface SubjectHandler {
    List<Subject> getDossierSubjectsByUri(URI pdfUri);
}
