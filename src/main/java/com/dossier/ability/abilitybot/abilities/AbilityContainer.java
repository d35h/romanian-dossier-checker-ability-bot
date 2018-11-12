package com.dossier.ability.abilitybot.abilities;

import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dossier.ability.abilitybot.constants.KeyboardLayoutConstants.*;
import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Psycho on 14.10.2018.
 */
public class AbilityContainer {

    public static Ability getKeyboardAbility(SilentSender silent) {
        return AbilityCreator.getPublicAbilityWithoutReply(START_COMMAND, START_COMMAND_INFO, silent, AbilityContainer::getKeyboardLayout);
    }

    public static Ability getFindAbility(SilentSender silent, BiConsumer<MessageContext, SilentSender> replyWhenSubjectFoundConsumer) {
        return AbilityCreator.getFindAbility(silent, replyWhenSubjectFoundConsumer);
    }

    public static Ability getInfoAbility(SilentSender silent) {
        return AbilityCreator.getPublicAbilityWithoutReply(BOT_INFO_COMMAND, BOT_INFO_COMMAND_INFO, silent, AbilityContainer::getInfoAbilityAction);
    }

    private static void getInfoAbilityAction(MessageContext context, SilentSender silent) {
        final SendMessage message = getMessage(context, BOT_INFO_COMMAND_INFO);
        silent.execute(message);
    }

    private static void getKeyboardLayout(MessageContext context, SilentSender silent) {
        final SendMessage welcomeMessage = getMessage(context, WELCOME_MESSAGE);
        final ReplyKeyboardMarkup keyboardMarkup = getReplyKeyboardMarkup();
        welcomeMessage.setReplyMarkup(keyboardMarkup);
        silent.execute(welcomeMessage);
    }

    private static ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        final ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        final List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow firstRow = getKeyboardRow(resolveKeyboardNames());
        keyboard.add(firstRow);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    private static List<String> resolveKeyboardNames() {
        return Stream.of(FIND_DOSSIER_COMMAND, BOT_INFO_COMMAND).map(AbilityContainer::addLeadingSlash).collect(Collectors.toList());
    }

    private static String addLeadingSlash(String name) {
        return "/" + name;
    }

    private static KeyboardRow getKeyboardRow(List<String> keyboardButtonNames) {
        KeyboardRow keyboardRow = new KeyboardRow();
        if (keyboardButtonNames == null || keyboardButtonNames.isEmpty()) {
            return keyboardRow;
        }

        keyboardButtonNames.forEach(keyboardRow::add);
        return keyboardRow;
    }

    private static SendMessage getMessage(MessageContext context, String messageText) {
        checkArgument(context != null, "Context must not be null");
        final SendMessage message = new SendMessage();
        message.setText(messageText);
        message.setChatId(context.chatId());
        return message;
    }

}
