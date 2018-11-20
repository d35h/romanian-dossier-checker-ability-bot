package com.dossier.ability.abilitybot.utils;

import com.dossier.ability.abilitybot.domain.Subject;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubjectUtils {

    private final static String REGEX_MATCHER_FOR_DOSSIER_ID_AND_YEAR = "\\((\\d*\\/\\d*)\\)";

    public final static String REGEX_MATCHER_FOR_DOSSIER = "^.*\\(\\d+\\/\\d+\\).*$";

    private final static String REGEX_LEADING_AND_TRAILING_WHITESPACES_AND_COMMA = "^[\\s]+|\\s+$|,";

    private final static String REGEX_REPLACE_DASH = "\\s?-\\s?";

    public static Subject getSubjectFromString(String candidate) {
        final String subjectId = getSubjectId(candidate);

        return Subject.builder()
                .id(subjectId)
                .build();
    }

    private static String getSubjectId(String subjectString) {
        subjectString = replaceAllLeadingTrailingSpacesFixDashFormatAndDeleteCommas(subjectString);
        subjectString = getDossierIdAndYear(subjectString);
        return subjectString;
    }

    private static String getDossierIdAndYear(String subjectString) {
        Pattern pattern = Pattern.compile(REGEX_MATCHER_FOR_DOSSIER_ID_AND_YEAR);
        Matcher matcher = pattern.matcher(subjectString);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return StringUtils.EMPTY;
    }

    private static String replaceAllLeadingTrailingSpacesFixDashFormatAndDeleteCommas(String stringToReplace) {
        final String dash = "-";
        return stringToReplace.replaceAll(REGEX_LEADING_AND_TRAILING_WHITESPACES_AND_COMMA, StringUtils.EMPTY).replaceAll(REGEX_REPLACE_DASH, dash);
    }

    public static boolean isStringMatched(String candidate, String regEx) {
        return Pattern.compile(regEx).matcher(candidate).matches();
    }
}
