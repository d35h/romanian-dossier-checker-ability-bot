package com.dossier.ability.abilitybot.services.data;

import com.dossier.ability.abilitybot.domain.Subject;
import com.dossier.ability.abilitybot.services.common.RomanianDossierCheckerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class SubjectServiceImpl implements SubjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RomanianDossierCheckerService.class);

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Optional<Subject> findById(String subjectId) {
        return subjectRepository.findById(subjectId);
    }

    @Override
    public Subject save(Subject subject) {
        if (subject == null) {
            LOGGER.info("Unable to save subject, subject object is null");
            return null;
        }

        LOGGER.info("Saving subject with id {}", subject.getId());
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public List<Subject> saveAll(List<Subject> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            LOGGER.info("Unable to save subject, subject object is null or empty");
            return null;
        }

        LOGGER.info("Saving subjects {}", subjects);
        return subjectRepository.saveAll(subjects);
    }
}
