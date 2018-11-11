package com.dossier.ability.abilitybot.services.data;

import com.dossier.ability.abilitybot.domain.LastProcessingDate;
import org.springframework.stereotype.Component;

/**
 * Created by Psycho on 05.11.2018.
 */
@Component
public class LastProcessingDateServiceImpl implements LastProcessingDateService {

    private final LastProcessingDateRepository repository;

    public LastProcessingDateServiceImpl(LastProcessingDateRepository repository) {
        this.repository = repository;
    }

    @Override
    public LastProcessingDate save(LastProcessingDate lastProcessingDate) {
        return repository.save(lastProcessingDate);
    }

    @Override
    public LastProcessingDate findFirstByDateIsNotNull() {
        return repository.findFirstByDateIsNotNull();
    }
}
