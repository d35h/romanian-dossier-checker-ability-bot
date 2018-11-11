package com.dossier.ability.abilitybot.abilities;

import org.apache.commons.lang3.StringUtils;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

import static com.google.common.base.Preconditions.checkArgument;
import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;

/**
 * Created by Psycho on 18.10.2018.
 */
public class AbilityCreator {
    public static final String DOSSIER_YEAR_ID_FORMAT = "^.*\\d+/\\d+$.*";
    private static final String FIND_COMMAND = "/find";
    private static final String INCORRECT_DOSSIER_ID_FORMAT_MESSAGE = "Please enter command '/find' following your dossier id in the following format: dossier id/year. Example: /find 123/2017";

    private static final Predicate<Update> messageStartsWithFind = update -> update.getMessage().getText().toLowerCase().startsWith(FIND_COMMAND);
    private static final Predicate<Update> messageHasCorrectFormat = update -> update.getMessage().getText().matches(DOSSIER_YEAR_ID_FORMAT);

    public static Ability getPublicAbilityWithoutReply(String abilityName, String abilityInfo, SilentSender silent, BiConsumer<MessageContext, SilentSender> consumer) {
        checkArgument(StringUtils.isNotEmpty(abilityName), "Ability name cannot be null or empty");
        checkArgument(silent != null, "Silent cannot be null");
        return getDefaultAbilityBuilder(abilityName, abilityInfo, silent, consumer).build();
    }

    public static Ability getFindAbility(String abilityName,
                                         String abilityInfo,
                                         SilentSender silent,
                                         BiConsumer<MessageContext, SilentSender> replyWhenSubjectFoundConsumer) {
        return Ability
                .builder()
                .name(abilityName)
                .info(abilityInfo)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(context -> replyWhenSubjectFoundConsumer.accept(context, silent))
                .reply(update -> getFindReplyWhenIncorrectFormat(update, silent), messageStartsWithFind, messageHasCorrectFormat.negate())
                .build();
    }

    private static Ability.AbilityBuilder getDefaultAbilityBuilder(String abilityName,
                                                                   String abilityInfo,
                                                                   SilentSender silent,
                                                                   BiConsumer<MessageContext, SilentSender> consumer) {
        return Ability
                .builder()
                .name(abilityName)
                .info(abilityInfo)
                .locality(ALL)
                .privacy(PUBLIC)
                .action(context -> consumer.accept(context, silent));
    }

    private static void getFindReplyWhenIncorrectFormat(Update update, SilentSender silent) {
        silent.send(INCORRECT_DOSSIER_ID_FORMAT_MESSAGE, update.getMessage().getChatId());
    }

}
