package com.qpg.converter.internal.styles.parsing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EscapeSequences {
    private static final Pattern PATTERN = Pattern.compile("\\\\(.)");

    public static String decode(String value) {
        Matcher matcher = PATTERN.matcher(value);
        StringBuilder decoded = new StringBuilder();
        int lastIndex = 0;
        while (matcher.find()) {
            decoded.append(value.substring(lastIndex, matcher.start()));
            decoded.append(escapeSequence(matcher.group(1)));
            lastIndex = matcher.end();
        }
        decoded.append(value.substring(lastIndex, value.length()));
        return decoded.toString();
    }

    private static char escapeSequence(String code) {
        switch (code) {
            case "n":
                return '\n';
            case "r":
                return '\r';
            case "t":
                return '\t';
            default:
                return code.charAt(0);
        }
    }
}
