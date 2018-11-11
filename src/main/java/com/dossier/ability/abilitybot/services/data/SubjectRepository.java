package com.dossier.ability.abilitybot.services.data;

import com.dossier.ability.abilitybot.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, String> {
}
