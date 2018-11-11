package com.dossier.ability.abilitybot;

import com.dossier.ability.abilitybot.abilities.AbilityCreator;
import com.dossier.ability.abilitybot.services.data.SubjectService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;

import java.util.function.BiConsumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by Psycho on 18.10.2018.
 */
public class AbilityCreatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenAbilityNameIsEmpty() {
        final BiConsumer<MessageContext, SilentSender> biConsumer = (cx, ss) -> {};
        AbilityCreator.getPublicAbilityWithoutReply(StringUtils.EMPTY, "info", new SilentSender(null), biConsumer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionWhenAbilityNameIsNull() {
        final BiConsumer<MessageContext, SilentSender> biConsumer = (cx, ss) -> {};
        AbilityCreator.getPublicAbilityWithoutReply("ability", "info", new SilentSender(null), biConsumer);
    }

    @Test
    public void shouldReturnPublicAbility() {
        final BiConsumer<MessageContext, SilentSender> biConsumer = (cx, ss) -> {};
        Ability actual = AbilityCreator.getPublicAbilityWithoutReply("name", "info", new SilentSender(null), biConsumer);

        assertThat(actual.name(), is("name"));
        assertThat(actual.info(), is("info"));
    }

}
