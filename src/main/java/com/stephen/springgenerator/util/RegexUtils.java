package com.stephen.springgenerator.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    private static final String NUMERIC_PATTERN = "[0-9]+";

    private static final String ARTIFACT_PATTERN = "([0-9]+\\.[0-9]+(\\.[0-9]+)?)(\\.[A-Za-z]+)?";

    public static boolean isNumeric(String s) {
        Pattern pattern = Pattern.compile(NUMERIC_PATTERN);
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }

    public static boolean isArtifactValid(String s) {
        Matcher matcher = getArtifactMatcher(s);
        return matcher.matches();
    }

    public static Matcher getArtifactMatcher(String s) {
        Pattern pattern = Pattern.compile(ARTIFACT_PATTERN);
        return pattern.matcher(s);
    }
}
