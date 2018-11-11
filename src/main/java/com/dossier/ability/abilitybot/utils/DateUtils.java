package com.dossier.ability.abilitybot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtils.class);

    public static Date tryToParse(String locale, String stringToParse, List<String> formats) {
        for (String format : formats) {
            try {
                LOGGER.info("Trying to parse {} with format {}", stringToParse, format);
                return new SimpleDateFormat(format, new Locale(locale)).parse(stringToParse);
            } catch (ParseException e) {
                LOGGER.info("Failed when trying to parse {} with format {}", stringToParse, format);
            }
        }
        return null;
    }
}
