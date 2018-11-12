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

    private static final String NOT_FOUND_TRY_AGAIN_MESSAGE = "I did not find you, try again later...";

    private final SubjectService subjectService;
    private final int creatorId;

    public DossierAbilityBot(@Value("${settings.bot.name}") String botUsername,
                             @Value("${settings.bot.token}") String botToken,
                             @Value("${settings.bot.creatorId}") int creatorId,
                             SubjectService subjectService) {
        super(botToken, botUsername);
        this.subjectService = subjectService;
        this.creatorId = creatorId;
    }

    @Override
    public int creatorId() {
        return creatorId;
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
            sender.send(foundSubject.map(this::getSubjectFoundMessage).orElse(getNotFoundMessage()), context.chatId());
        }
    }

    private String getSubjectFoundMessage(Subject subject) {
        return "You are on the list" + subject.getPdfUri();
    }

    private String getNotFoundMessage() {
        return NOT_FOUND_TRY_AGAIN_MESSAGE;
    }

}
