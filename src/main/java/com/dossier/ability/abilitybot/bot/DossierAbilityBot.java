package com.dossier.ability.abilitybot.bot;

import com.dossier.ability.abilitybot.abilities.AbilityContainer;
import com.dossier.ability.abilitybot.domain.Subject;
import com.dossier.ability.abilitybot.services.data.SubjectService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;

import java.util.Optional;

import static com.dossier.ability.abilitybot.abilities.AbilityCreator.DOSSIER_YEAR_ID_FORMAT;

/**
 * Created by Psycho on 10.10.2018.
 */
@Service
public class DossierAbilityBot extends AbilityBot {

    private final SubjectService subjectService;

    public DossierAbilityBot(@Value("${settings.bot.name}") String botUsername,
                             @Value("${settings.bot.token}") String botToken,
                             SubjectService subjectService) {
        super(botToken, botUsername);
        this.subjectService = subjectService;
    }

    @Override
    public int creatorId() {
        return 0;
    }

    public Ability sendKeyboardLayout() {
        return AbilityContainer.getKeyboardAbility(silent);
    }

    public Ability sendInfoAbility() {
        return AbilityContainer.getInfoAbility(silent);
    }

    public Ability sendFindAbility() {
        return AbilityContainer.getFindAbility(silent, this::replyWhenSubjectFound);
    }

    private void replyWhenSubjectFound(MessageContext context, SilentSender sender) {
        if (context.firstArg().matches(DOSSIER_YEAR_ID_FORMAT)) {
            Optional<Subject> foundSubject = subjectService.findById(context.firstArg());
            sender.send(foundSubject.isPresent() ? "You are on the list: " + foundSubject.get().getPdfUri() : "I did not find you, try again later...", context.chatId());
        }
    }

}
