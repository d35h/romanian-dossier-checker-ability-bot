package com.dossier.ability.abilitybot.services.data;

import com.dossier.ability.abilitybot.domain.Subject;

import java.util.List;
import java.util.Optional;

/**
 * Interface to avoid cyclic cycling injection
 */
public interface SubjectService {

    /**
     * Looks for subject entity by a specified subject id.
     *
     * @param subjectId a subject ID look to by.
     * @return subject which was found by a specified ID.
     */
    Optional<Subject> findById(String subjectId);

    /**
     * Persists a subject entity to the database and returns saved entity.
     *
     * @param subject a Subject to persist.
     * @return persisted subject.
     */
    Subject save(Subject subject);

    /**
     * Persists a subject entities to the database and returns all saved subjects.
     *
     * @param subjects to persist.
     * @return list of subjects which were saved
     */
    List<Subject> saveAll(List<Subject> subjects);
}
