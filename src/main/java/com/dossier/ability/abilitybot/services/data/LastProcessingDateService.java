package com.dossier.ability.abilitybot.services.data;

import com.dossier.ability.abilitybot.domain.LastProcessingDate;

import java.util.Date;

/**
 * Created by Psycho on 05.11.2018.
 */
public interface LastProcessingDateService {
    /**
     * Looks for last processing date entity by a date.
     *
     * @return last processing date which was found by a specified date.
     */
    LastProcessingDate findFirstByDateIsNotNull();

    LastProcessingDate save(LastProcessingDate lastProcessingDate);

}
