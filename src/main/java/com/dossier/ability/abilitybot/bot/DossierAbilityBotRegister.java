package com.dossier.ability.abilitybot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

/**
 * Created by Psycho on 10.10.2018.
 */
@Component
public class DossierAbilityBotRegister implements CommandLineRunner {

    private final DossierAbilityBot dossierAbilityBot;

    @Autowired
    public DossierAbilityBotRegister(DossierAbilityBot dossierAbilityBot) {
        this.dossierAbilityBot = dossierAbilityBot;
    }

    @Override
    public void run(String... args) throws Exception {
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(dossierAbilityBot);
    }

}
