package com.dossier.ability.abilitybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
@EnableScheduling
public class AbilityBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(AbilityBotApplication.class, args);
	}
}
