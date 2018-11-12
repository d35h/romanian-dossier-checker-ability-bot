package com.dossier.ability.abilitybot.abilities;

import com.dossier.ability.abilitybot.abilities.AbilityCreator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;

import java.util.function.BiConsumer;

import static com.dossier.ability.abilitybot.constants.KeyboardLayoutConstants.FIND_DOSSIER_COMMAND;
import static com.dossier.ability.abilitybot.constants.KeyboardLayoutConstants.FIND_DOSSIER_COMMAND_INFO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Psycho on 18.10.2018.
 */
public class AbilityCreatorTest {

    @Test
    @DisplayName("Should return correct ability object")
    public void shouldReturnPublicAbility() {
        final BiConsumer<MessageContext, SilentSender> biConsumer = (cx, ss) -> {};
        Ability actual = AbilityCreator.getPublicAbilityWithoutReply("name", "info", new SilentSender(null), biConsumer);

        assertThat(actual.name(), is("name"));
        assertThat(actual.info(), is("info"));
    }

    @Test
    @DisplayName("Should return correct find ability object")
    public void shouldReturnFindAbility() {
        final BiConsumer<MessageContext, SilentSender> biConsumer = (cx, ss) -> {};
        Ability actual = AbilityCreator.getFindAbility(new SilentSender(null), biConsumer);

        assertThat(actual.name(), is(FIND_DOSSIER_COMMAND));
        assertThat(actual.info(), is(FIND_DOSSIER_COMMAND_INFO));
    }

}
