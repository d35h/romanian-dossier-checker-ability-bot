package com.dossier.ability.abilitybot.services.data;

import com.dossier.ability.abilitybot.domain.LastProcessingDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Created by Psycho on 05.11.2018.
 */
public interface LastProcessingDateRepository extends JpaRepository<LastProcessingDate, Long> {

    LastProcessingDate findFirstByDateIsNotNull();

}
